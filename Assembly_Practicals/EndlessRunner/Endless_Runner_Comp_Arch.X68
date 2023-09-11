*-----------------------------------------------------------
* Title      : Jumping Amongus
* Written by : Christopher Byrne
* Student ID : C00276260
* Date       : 7/03/2023
* Description: Simple jump game that gets progressivle harder as time passes

*Known Bugs: Graphical error when player is on the ground causing sprite to stretch and fill the screen, 
*            on further investigation this was an issue with the d4 register when performing the add.b on d4
*            the value would only hold the least significant bit causing the graphics to bug out and stretch to top of screen
*            resolved this but putting in literal values instead of relying on add for d4
*            Gun shot does not play on first shot of cannon
*            Graphical glitches on die animation(sometimes)
*-----------------------------------------------------------
    ORG    $1000
START:                  ; first instruction of program

*-----------------------------------------------------------
* Section       : Trap Codes
* Description   : Trap Codes used throughout StarterKit
*-----------------------------------------------------------
* Trap CODES
TC_SCREEN   EQU         33          ; Screen size information trap code
TC_S_SIZE   EQU         00          ; Places 0 in D1.L to retrieve Screen width and height in D1.L
                                    ; First 16 bit Word is screen Width and Second 16 bits is screen Height
TC_KEYCODE  EQU         19          ; Check for pressed keys
TC_DBL_BUF  EQU         92          ; Double Buffer Screen Trap Code
TC_CURSR_P  EQU         11          ; Trap code cursor position

TC_TIME     EQU         08          ;Trap code to get current time since midnight in centiseconds

TC_EXIT     EQU         09          ; Exit Trapcode

*-----------------------------------------------------------
* Section       : Charater Setup
* Description   : Size of Player and Enemy and properties
* of these characters e.g Starting Positions and Sizes
*-----------------------------------------------------------
PLYR_W_INIT EQU         20          ; Players initial Width
PLYR_H_INIT EQU         24          ; Players initial Height

PLYR_DFLT_V EQU         00          ; Default Player Velocity
PLYR_JUMP_V EQU        -10          ; Player Jump Velocity
PLYR_DFLT_G EQU         01          ; Player Default Gravity

GND_TRUE    EQU         01          ; Player on Ground True
GND_FALSE   EQU         00          ; Player on Ground False

SHOT_INDEX   EQU         00          ; Player Run Sound Index  
JMP_INDEX   EQU         01          ; Player Jump Sound Index  
DIE_INDEX  EQU         02          ; Player Opps Sound Index

ENMY_W_INIT EQU         08          ; Enemy initial Width
ENMY_H_INIT EQU         08          ; Enemy initial Height
ENMY_MX_SPD EQU         32          ; Enemy max speed

PLR_GND_X   EQU        320          ; Constant for ground x val as using player_x caused graphical error
PLR_GND_Y   EQU        240          ; Constant for ground y val as using player_y caused graphical error


*-----------------------------------------------------------
* Section       : Game Stats
* Description   : Points
*-----------------------------------------------------------
POINTS      EQU         01          ; Points added
FRAME_TIME  EQU         03          ; For getting slower frame time

*-----------------------------------------------------------
* Section       : Keyboard Keys
* Description   : Spacebar and Escape or two functioning keys
* Spacebar to JUMP and Escape to Exit Game
*-----------------------------------------------------------
SPACEBAR    EQU         $20         ; Spacebar ASCII Keycode
ESCAPE      EQU         $1B         ; Escape ASCII Keycode

*-----------------------------------------------------------
* Subroutine    : Initialise
* Description   : Initialise game data into memory such as 
* sounds and screen size
*-----------------------------------------------------------
INITIALISE:
    ; Initialise Sounds
    BSR     SHOT_LOAD                ; Load Run Sound into Memory
    BSR     JUMP_LOAD               ; Load Jump Sound into Memory
    BSR     DIE_LOAD               ; Load Opps (Collision) Sound into Memory

    ; Screen Size
    MOVE.B  #TC_SCREEN, D0          ; access screen information
    MOVE.L  #TC_S_SIZE, D1          ; placing 0 in D1 triggers loading screen size information
    TRAP    #15                     ; interpret D0 and D1 for screen size
    MOVE.W  D1,         SCREEN_H    ; place screen height in memory location
    SWAP    D1                      ; Swap top and bottom word to retrive screen size
    MOVE.W  D1,         SCREEN_W    ; place screen width in memory location

    ; Place the Player at the center of the screen
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.W  SCREEN_W,   D1          ; Place Screen width in D1
    DIVU    #02,        D1          ; divide by 2 for center on X Axis
    MOVE.L  D1,         PLAYER_X    ; Players X Position

    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.W  SCREEN_H,   D1          ; Place Screen width in D1
    DIVU    #02,        D1          ; divide by 2 for center on Y Axis
    MOVE.L  D1,         PLAYER_Y    ; Players Y Position

    ; Initialise Player Score
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.L  #00,        D1          ; Init Score
    MOVE.L  D1,         PLAYER_SCORE

    ; Initialise Player Velocity
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.B  #PLYR_DFLT_V,D1         ; Init Player Velocity
    MOVE.L  D1,         PLYR_VELOCITY

    ; Initialise Player Gravity
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.L  #PLYR_DFLT_G,D1         ; Init Player Gravity
    MOVE.L  D1,         PLYR_GRAVITY

    ; Initialize Player on Ground
    MOVE.L  #GND_TRUE,  PLYR_ON_GND ; Init Player on Ground

    ; Initial Position for Enemy
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.W  SCREEN_W,   D1          ; Place Screen width in D1
    MOVE.L  D1,         ENEMY_X     ; Enemy X Position
    MOVE.L  #5, ENEMY_SPEED        ; Enemy speed init

    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.W  SCREEN_H,   D1          ; Place Screen width in D1
    DIVU    #02,        D1          ; divide by 2 for center on Y Axis
    MOVE.L  D1,         ENEMY_Y     ; Enemy Y Position

    ; Enable the screen back buffer(see easy 68k help)
	MOVE.B  #TC_DBL_BUF,D0          ; 92 Enables Double Buffer
    MOVE.B  #17,        D1          ; Combine Tasks
	TRAP	#15                     ; Trap (Perform action)

    ; Clear the screen (see easy 68k help)
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
	MOVE.W  #$FF00,     D1          ; Fill Screen Clear
	TRAP	#15                     ; Trap (Perform action)
	
	
	

*-----------------------------------------------------------
* Subroutine    : Game
* Description   : Game including main GameLoop. GameLoop is like
* a while loop in that it runs forever until interupted
* (Input, Update, Draw). The Enemies Run at Player Jump to Avoid
* ADDED PRE GAME LOOP TO HAVE A MENU BEFORE GAME START
*-----------------------------------------------------------

GAME:


*-----------------------------------------------------------
* Subroutine    : PRE GAME LOOP
* Description   : MENU TO ALLOW USER CHOOSE WHEN GAME STARTS AND ALERT TO THE CONTROLS
*-----------------------------------------------------------    
    
    
PRE_GAME_LOOP:
    ; PRE GAME LOOP
    BSR     PRE_GAME_DRAW           ; Draw the Scene
    BSR     PRE_GAME_INPUT          ; Check Keyboard Input
    BRA     GAMELOOP           ; Loop back to GameLoop
    
PRE_GAME_INPUT:
    ; Process Input
    CLR.L   D1                      ; Clear Data Register
    MOVE.B  #TC_KEYCODE,D0          ; Listen for Keys
    TRAP    #15                     ; Trap (Perform action)
    MOVE.B  D1,         D2          ; Move last key D1 to D2
    CMP.B   #00,        D2          ; Key is pressed
    BEQ     PG_PROCESS_INPUT           ; Process Key
    TRAP    #15                     ; Trap for Last Key
    ; Check if key still pressed
    CMP.B   #$FF,       D1          ; Is it still pressed
    BEQ     PROCESS_INPUT           ; Process Last Key
    RTS                             ; Return to subroutine
    

    
*-----------------------------------------------------------
* Subroutine    : PRE GAME Process Input
* Description   : Branch based on keys pressed
*-----------------------------------------------------------
PG_PROCESS_INPUT:
    MOVE.L  D2,         CURRENT_KEY ; Put Current Key in Memory
    CMP.L   #ESCAPE,    CURRENT_KEY ; Is Current Key Escape
    BEQ     EXIT                    ; Exit if Escape
    CMP.L   #SPACEBAR,  CURRENT_KEY ; Is Current Key Spacebar
    BEQ     PLAY_SHOT
    BEQ     GAMELOOP               ; Enter game loop
    BRA     PRE_GAME_LOOP           ; Loop back and wait for input
    RTS                             ; Return to subroutine

*-----------------------------------------------------------
* Subroutine    : GAMELOOP
* Description   : MAIN GAME LOOP STARTS HERE
*-----------------------------------------------------------
    
GAMELOOP:
    ; Main Gameloop
    BSR     INPUT                   ; Check Keyboard Input
    BSR     UPDATE                  ; Update positions and points
    BSR     IS_PLAYER_ON_GND        ; Check if player is on ground
    BSR     CHECK_COLLISIONS        ; Check for Collisions
    BSR     WAIT_FOR_NEXT_FRAME     ; Wait for the next frame before drawing the scene
    BSR     DRAW                    ; Draw the Scene
    BRA     GAMELOOP                ; Loop back to GameLoop

*-----------------------------------------------------------
* Subroutine    : Input
* Description   : Process Keyboard Input
*-----------------------------------------------------------
INPUT:
    ; Process Input
    CLR.L   D1                      ; Clear Data Register
    MOVE.B  #TC_KEYCODE,D0          ; Listen for Keys
    TRAP    #15                     ; Trap (Perform action)
    MOVE.B  D1,         D2          ; Move last key D1 to D2
    CMP.B   #00,        D2          ; Key is pressed
    BEQ     PROCESS_INPUT           ; Process Key
    TRAP    #15                     ; Trap for Last Key
    ; Check if key still pressed
    CMP.B   #$FF,       D1          ; Is it still pressed
    BEQ     PROCESS_INPUT           ; Process Last Key
    RTS                             ; Return to subroutine

*-----------------------------------------------------------
* Subroutine    : Process Input
* Description   : Branch based on keys pressed
*-----------------------------------------------------------
PROCESS_INPUT:
    MOVE.L  D2,         CURRENT_KEY ; Put Current Key in Memory
    CMP.L   #ESCAPE,    CURRENT_KEY ; Is Current Key Escape
    BEQ     EXIT                    ; Exit if Escape
    CMP.L   #SPACEBAR,  CURRENT_KEY ; Is Current Key Spacebar
    BEQ     JUMP                    ; Jump
    BRA     IDLE                    ; Or Idle
    RTS                             ; Return to subroutine

*-----------------------------------------------------------
* Subroutine    : Update
* Description   : Main update loop update Player and Enemies
*-----------------------------------------------------------
UPDATE:
    ; Update the Players Positon based on Velocity and Gravity
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.L  PLYR_VELOCITY, D1       ; Fetch Player Velocity
    MOVE.L  PLYR_GRAVITY, D2        ; Fetch Player Gravity
    ADD.L   D2,         D1          ; Add Gravity to Velocity
    MOVE.L  D1,         PLYR_VELOCITY ; Update Player Velocity
    ADD.L   PLAYER_Y,   D1          ; Add Velocity to Player
    MOVE.L  D1,         PLAYER_Y    ; Update Players Y Position 

    ; Move the Enemy
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    CLR.L   D1                      ; Clear the contents of D0
    MOVE.L  ENEMY_X,    D1          ; Move the Enemy X Position to D0
    CMP.L   #00,        D1
    BLE     RESET_ENEMY_POSITION    ; Reset Enemy if off Screen
    BRA     MOVE_ENEMY              ; Move the Enemy
    

    RTS                             ; Return to subroutine  
    
    
*-----------------------------------------------------------
* Subroutine    : WAIT_FOR_NEXT_FRAME
* Description   : Slow the movement of the game
*-----------------------------------------------------------
WAIT_FOR_NEXT_FRAME:
    MOVE.B  #TC_TIME, D0
    TRAP    #15
    MOVE.L  D1, D2
    MOVE.L  LAST_FRAME_TIME, D0
    SUB.L   D0, D1
    CMP.L   #FRAME_TIME, D1
    BLE     WAIT_FOR_NEXT_FRAME
    MOVE.L  D2, LAST_FRAME_TIME
    RTS

*-----------------------------------------------------------
* Subroutine    : Move Enemy
* Description   : Move Enemy Right to Left
*-----------------------------------------------------------
MOVE_ENEMY:
    MOVE.L  ENEMY_SPEED, D6
    SUB.L   D6,        ENEMY_X     ; Move enemy by ENEMY_SPEED Value
    RTS

*-----------------------------------------------------------
* Subroutine    : Reset Enemy
* Description   : Reset Enemy if to passes 0 to Right of Screen
*-----------------------------------------------------------
RESET_ENEMY_POSITION:
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.W  SCREEN_W,   D1          ; Place Screen width in D1
    MOVE.L  D1,         ENEMY_X     ; Enemy X Position
    MOVE.L  ENEMY_SPEED, D6
    ADD.L   #01, D6
    ;UPDATE PLAYERS POINTS BASED ON ENEMIES PASSED
    MOVE.L  D6, ENEMY_SPEED
    CLR.L   D3
    MOVE.B  #POINTS, D3
    ADD.L   PLAYER_SCORE, D3
    MOVE.L  D3, PLAYER_SCORE
    BSR     PLAY_SHOT
    RTS
    
*-----------------------------------------------------------
* Subroutine    : Draw
* Description   : Draw Screen
*-----------------------------------------------------------
PRE_GAME_DRAW: 
    ; Enable back buffer
    MOVE.B  #94,        D0
    TRAP    #15

    ; Clear the screen
    MOVE.B	#TC_CURSR_P,D0          ; Set Cursor Position
	MOVE.W	#$FF00,     D1          ; Clear contents
	TRAP    #15                     ; Trap (Perform action)

    BSR     DRAW_BCKGRND            ; Draw background enviroment
    BSR     DRAW_MENU               ; Draw a menu with some info on controls
    BSR     DRAW_PLAYER             ; Draw Player
    BSR     DRAW_ENEMY              ; Draw Enemy
    BSR     DRAW_CANON
    RTS                             ; Return to subroutine

*-----------------------------------------------------------
* Subroutine    : Draw
* Description   : Draw Screen
*-----------------------------------------------------------
DRAW: 
    ; Enable back buffer
    MOVE.B  #94,        D0
    TRAP    #15

    ; Clear the screen
    MOVE.B	#TC_CURSR_P,D0          ; Set Cursor Position
	MOVE.W	#$FF00,     D1          ; Clear contents
	TRAP    #15                     ; Trap (Perform action)

    BSR     DRAW_BCKGRND            ; Draw background enviroment
    BSR     DRAW_PLYR_DATA          ; Draw Draw Score, HUD, Player X and Y
    BSR     DRAW_PLAYER             ; Draw Player
    BSR     DRAW_ENEMY              ; Draw Enemy
    BSR     DRAW_CANON
    RTS                             ; Return to subroutine
    
*-----------------------------------------------------------
* Subroutine    : Draw_GAME_OVER
* Description   : Draw Screen
*-----------------------------------------------------------
DRAW_GAME_OVER: 
    ; Enable back buffer
    MOVE.B  #94,        D0
    TRAP    #15

    ; Clear the screen
    MOVE.B	#TC_CURSR_P,D0          ; Set Cursor Position
	MOVE.W	#$FF00,     D1          ; Clear contents
	TRAP    #15                     ; Trap (Perform action)

    BSR     DRAW_BCKGRND            ; Draw background enviroment
    BSR     DRAW_GAME_OVER_MESSAGE  ; GAME OVER MESSAGE
    BSR     PLAYER_JUMP             ; Draw Player
    BSR     DRAW_ENEMY              ; Draw Enemy
    RTS                             ; Return to subroutine
    
*-----------------------------------------------------------
* Subroutine    : DRAW_MENU
* Description   : Draw the menu for pre game loop
*-----------------------------------------------------------
DRAW_MENU:
    CLR.L   D1
    
    ; GAME TITLE
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$2004,     D1          ; Col 02, Row 01
    TRAP    #15                     ; Trap (Perform action)
    LEA     GAME_TITLE,  A1          ; Score Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)
    
    ; PRESS SPACE TO JUMP
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$1E06,     D1          ; Col 02, Row 01
    TRAP    #15                     ; Trap (Perform action)
    LEA     JUMP_INST,  A1          ; Score Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15   
    
    ; PRESS ESC TO EXIT
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$1F08,     D1          ; Col 02, Row 01
    TRAP    #15                     ; Trap (Perform action)
    LEA     EXIT_INST,  A1          ; Score Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15  
    
    ; PRESS SPACE TO JUMP
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$170B,     D1          ; Col 02, Row 01
    TRAP    #15                     ; Trap (Perform action)
    LEA     START_INST,  A1          ; Score Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15
    
    RTS
    
*-----------------------------------------------------------
* Subroutine    : DRAW_MENU
* Description   : Draw the menu for pre game loop
*-----------------------------------------------------------
DRAW_GAME_OVER_MESSAGE:
    ; GAME OVER
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$2004,     D1          ; Col 02, Row 01
    TRAP    #15                     ; Trap (Perform action)
    LEA     GAME_OVER,  A1          ; Score Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)
    
    ; YOUR SCORE
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$1E06,     D1          ; Col 02, Row 01
    TRAP    #15                     ; Trap (Perform action)
    LEA     YOUR_SCORE,  A1          ; Score Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15   
    
    ; Player Score Value
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$2D06,     D1          ; Col 09, Row 01
    TRAP    #15                     ; Trap (Perform action)
    MOVE.B  #03,        D0          ; Display number at D1.L
    MOVE.L  PLAYER_SCORE,D1         ; Move Score to D1.L
    TRAP    #15                     ; Trap (Perform action)
    
    
    RTS
    
*-----------------------------------------------------------
* Subroutine    : DRAW_BCKGRND
* Description   : Draw the sky and ground for the environment
*-----------------------------------------------------------
DRAW_BCKGRND:
    
    CLR.L   D1
    ;Set pen colour and fill colour to blue
    MOVE.B  #80, D0
    MOVE.L  #BLUE, D1
    TRAP    #15
    
    MOVE.B  #81, D0
    TRAP    #15
    
    ;Draw sky
    MOVE.B  #87, D0
    MOVE.W  #0, D1
    MOVE.W  #0, D2
    MOVE.W  #SCREEN_W, D3
    MOVE.W  #SCREEN_H, D4
    TRAP    #15 
    
    ;Set pen colour and fill colour to green
    MOVE.B  #80, D0
    MOVE.L  #GREEN, D1
    TRAP    #15
    
    MOVE.B  #81, D0
    TRAP    #15
    
    ;Draw grass
    MOVE.B  #87, D0
    MOVE.W  #0, D1
    MOVE.L  #265, D2
    MOVE.W  #SCREEN_W, D3
    MOVE.W  #SCREEN_H, D4
    TRAP    #15 
    
    ;Set pen colour and fill colour to brown
    MOVE.B  #80, D0
    MOVE.L  #BROWN, D1
    TRAP    #15
    
    MOVE.B  #81, D0
    TRAP    #15
    
    ;Draw ground
    MOVE.B  #87, D0
    MOVE.W  #0, D1
    MOVE.W  #300, D2
    MOVE.W  #SCREEN_W, D3
    MOVE.W  #SCREEN_H, D4
    TRAP    #15 
    
    ;Set pen colour and fill colour back to blue
    MOVE.B  #80, D0
    MOVE.L  #BLUE, D1
    TRAP    #15
    
    MOVE.B  #81, D0
    TRAP    #15
    
    RTS
    

*-----------------------------------------------------------
* Subroutine    : Draw Player Data
* Description   : Draw Player X, Y, Velocity, Gravity and OnGround
*-----------------------------------------------------------
DRAW_PLYR_DATA:
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    
    ; YOUR SCORE
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$1E06,     D1          ; Col 02, Row 01
    TRAP    #15                     ; Trap (Perform action)
    LEA     YOUR_SCORE,  A1          ; Score Message
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15   
    
    ; Player Score Value
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$2D06,     D1          ; Col 09, Row 01
    TRAP    #15                     ; Trap (Perform action)
    MOVE.B  #03,        D0          ; Display number at D1.L
    MOVE.L  PLAYER_SCORE,D1         ; Move Score to D1.L
    TRAP    #15                     ; Trap (Perform action)

    

    RTS  
    
*-----------------------------------------------------------
* Subroutine    : Player is on Ground
* Description   : Check if the Player is on or off Ground
*-----------------------------------------------------------
IS_PLAYER_ON_GND:
    ; Check if Player is on Ground
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    CLR.L   D2                      ; Clear contents of D2 (XOR is faster)
    MOVE.W  SCREEN_H,   D1          ; Place Screen width in D1
    DIVU    #02,        D1          ; divide by 2 for center on Y Axis
    MOVE.L  PLAYER_Y,   D2          ; Player Y Position
    CMP     D1,         D2          ; Compare middle of Screen with Players Y Position 
    BGE     SET_ON_GROUND           ; The Player is on the Ground Plane
    BLT     SET_OFF_GROUND          ; The Player is off the Ground
    RTS                             ; Return to subroutine


*-----------------------------------------------------------
* Subroutine    : On Ground
* Description   : Set the Player On Ground
*-----------------------------------------------------------
SET_ON_GROUND:
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.W  SCREEN_H,   D1          ; Place Screen width in D1
    DIVU    #02,        D1          ; divide by 2 for center on Y Axis
    MOVE.L  D1,         PLAYER_Y    ; Reset the Player Y Position
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    MOVE.L  #00,        D1          ; Player Velocity
    MOVE.L  D1,         PLYR_VELOCITY ; Set Player Velocity
    MOVE.L  #GND_TRUE,  PLYR_ON_GND ; Player is on Ground
    RTS

*-----------------------------------------------------------
* Subroutine    : Off Ground
* Description   : Set the Player Off Ground
*-----------------------------------------------------------
SET_OFF_GROUND:
    MOVE.L  #GND_FALSE, PLYR_ON_GND ; Player if off Ground
    RTS                             ; Return to subroutine
*-----------------------------------------------------------
* Subroutine    : Jump
* Description   : Perform a Jump
*-----------------------------------------------------------
JUMP:
    CMP.L   #GND_TRUE,PLYR_ON_GND   ; Player is on the Ground ?
    BEQ     PERFORM_JUMP            ; Do Jump
    BRA     JUMP_DONE               ;
PERFORM_JUMP:
    BSR     PLAY_JUMP               ; Play jump sound
    MOVE.L  #PLYR_JUMP_V,PLYR_VELOCITY ; Set the players velocity to true
    RTS                             ; Return to subroutine   
DEATH_JUMP:

    MOVE.L  #PLYR_JUMP_V,PLYR_VELOCITY ; Set the players velocity to true
    RTS                             ; Return to subroutine
JUMP_DONE:
    RTS                             ; Return to subroutine

*-----------------------------------------------------------
* Subroutine    : Idle
* Description   : Perform a Idle
*----------------------------------------------------------- 
IDLE:
    RTS                             ; Return to subroutine

*-----------------------------------------------------------
* Subroutines   : Sound Load and Play
* Description   : Initialise game sounds into memory 
* Current Sounds are RUN, JUMP and Opps for Collision
*-----------------------------------------------------------
SHOT_LOAD:
    LEA     SHOT_WAV,    A1          ; Load Wav File into A1
    MOVE    #SHOT_INDEX, D1          ; Assign it INDEX
    MOVE    #71,        D0          ; Load into memory
    TRAP    #15                     ; Trap (Perform action)
    RTS                             ; Return to subroutine

PLAY_SHOT:
    MOVE    #SHOT_INDEX, D1          ; Load Sound INDEX
    MOVE    #72,        D0          ; Play Sound
    TRAP    #15                     ; Trap (Perform action)
    RTS                             ; Return to subroutine

JUMP_LOAD:
    LEA     JUMP_WAV,   A1          ; Load Wav File into A1
    MOVE    #JMP_INDEX, D1          ; Assign it INDEX
    MOVE    #71,        D0          ; Load into memory
    TRAP    #15                     ; Trap (Perform action)
    RTS                             ; Return to subroutine

PLAY_JUMP:
    MOVE    #JMP_INDEX, D1          ; Load Sound INDEX
    MOVE    #72,        D0          ; Play Sound
    TRAP    #15                     ; Trap (Perform action)
    RTS                             ; Return to subroutine

DIE_LOAD:
    LEA     DIE_WAV,   A1          ; Load Wav File into A1
    MOVE    #DIE_INDEX,D1          ; Assign it INDEX
    MOVE    #71,        D0          ; Load into memory
    TRAP    #15                     ; Trap (Perform action)
    RTS                             ; Return to subroutine

PLAY_DIE:
    MOVE    #DIE_INDEX,D1          ; Load Sound INDEX
    MOVE    #72,        D0          ; Play Sound
    TRAP    #15                     ; Trap (Perform action)
    RTS                             ; Return to subroutine

*-----------------------------------------------------------
* Subroutine    : PLAYER_STAND
* Description   : Draw Player Standing
*-----------------------------------------------------------
PLAYER_STAND:
    
    
    CLR.L   D0
    MOVE.B  #80, D0
    MOVE.L  #BLACK, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  #PLR_GND_X, D1
    MOVE.L  #PLR_GND_Y, D2
    MOVE.L  #PLR_GND_X, D3
    MOVE.L  #264, D4
    ADD.B   #3, D1
    ADD.B   #1, D2
    ADD.B   #15, D3
    TRAP    #15

    MOVE.B  #80, D0
    MOVE.L  #PURPLE, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15  
    
    MOVE.B  #87, D0
    MOVE.L  #PLR_GND_X, D1
    MOVE.L  #PLR_GND_Y, D2
    MOVE.L  #PLR_GND_X, D3
    MOVE.L  #264, D4
    ADD.B   #4, D1
    ADD.B   #2, D2
    ADD.B   #14, D3
    TRAP    #15  

    MOVE.B  #80, D0
    MOVE.L  #BLACK, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15   

    MOVE.B  #87, D0
    MOVE.L  #PLR_GND_X, D1
    MOVE.L  #PLR_GND_Y, D2
    MOVE.L  #PLR_GND_X, D3
    MOVE.L  #257, D4
    ADD.B   #7, D2
    ADD.B   #3, D3
    TRAP    #15
    
    MOVE.B  #80, D0
    MOVE.L  #PURPLE, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  #PLR_GND_X, D1
    MOVE.L  #PLR_GND_Y, D2
    MOVE.L  #PLR_GND_X, D3
    MOVE.L  #256, D4
    ADD.B   #1, D1
    ADD.B   #8, D2
    ADD.B   #3, D3
    TRAP    #15
    
    MOVE.B  #80, D0
    MOVE.L  #BLACK, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  #PLR_GND_X, D1
    MOVE.L  #PLR_GND_Y, D2
    MOVE.L  #PLR_GND_X, D3
    MOVE.L  #251, D4
    ADD.B   #7, D1
    ADD.B   #5, D2
    ADD.B   #16, D3
    TRAP    #15
    
    MOVE.B  #80, D0
    MOVE.L  #BLUE, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  #PLR_GND_X, D1
    MOVE.L  #PLR_GND_Y, D2
    MOVE.L  #PLR_GND_X, D3
    MOVE.L  #250, D4
    ADD.B   #8, D1
    ADD.B   #6, D2
    ADD.B   #15, D3
    TRAP    #15
    
    MOVE.B  #80, D0
    MOVE.L  #BLACK, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  #PLR_GND_X, D1
    MOVE.L  #259, D2
    MOVE.L  #PLR_GND_X, D3
    MOVE.L  #264, D4
    ADD.B   #7, D1
    ADD.B   #11, D3
    TRAP    #15
    
    MOVE.B  #80, D0
    MOVE.L  #BLUE, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  #328, D1
    MOVE.L  #260, D2
    MOVE.L  #330, D3
    MOVE.L  #264, D4
    TRAP    #15
    
    MOVE.B  #80, D0
    MOVE.L  #BLUE, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    RTS

*-----------------------------------------------------------
* Subroutine    : PLAYER_JUMP
* Description   : Draw Player Jumping
*-----------------------------------------------------------
PLAYER_JUMP:
    
    CLR.L   D0     
    MOVE.B  #80, D0
    MOVE.L  #BLACK, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    MOVE.L  PLAYER_X, D3
    MOVE.L  PLAYER_Y, D4
    ADD.B   #3, D1
    ADD.B   #1, D2
    ADD.B   #15, D3
    ADD.B   #20, D4
    TRAP    #15

    MOVE.B  #80, D0
    MOVE.L  #PURPLE, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15  
    
    MOVE.B  #87, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    MOVE.L  PLAYER_X, D3
    MOVE.L  PLAYER_Y, D4
    ADD.B   #4, D1
    ADD.B   #2, D2
    ADD.B   #14, D3
    ADD.B   #19, D4
    TRAP    #15  

    MOVE.B  #80, D0
    MOVE.L  #BLACK, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15   

    MOVE.B  #87, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    MOVE.L  PLAYER_X, D3
    MOVE.L  PLAYER_Y, D4
    ADD.B   #7, D2
    ADD.B   #3, D3
    ADD.B   #17, D4
    TRAP    #15
    
    MOVE.B  #80, D0
    MOVE.L  #PURPLE, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    MOVE.L  PLAYER_X, D3
    MOVE.L  PLAYER_Y, D4
    ADD.B   #1, D1
    ADD.B   #8, D2
    ADD.B   #3, D3
    ADD.B   #16, D4
    TRAP    #15
    
    MOVE.B  #80, D0
    MOVE.L  #BLACK, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    MOVE.L  PLAYER_X, D3
    MOVE.L  PLAYER_Y, D4
    ADD.B   #7, D1
    ADD.B   #5, D2
    ADD.B   #16, D3
    ADD.B   #11, D4
    TRAP    #15
    
    MOVE.B  #80, D0
    MOVE.L  #BLUE, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    MOVE.L  PLAYER_X, D3
    MOVE.L  PLAYER_Y, D4
    ADD.B   #8, D1
    ADD.B   #6, D2
    ADD.B   #15, D3
    ADD.B   #10, D4
    TRAP    #15
    
    MOVE.B  #80, D0
    MOVE.L  #BLACK, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    MOVE.L  PLAYER_X, D3
    MOVE.L  PLAYER_Y, D4
    ADD.B   #3, D1
    ADD.B   #16, D2
    ADD.B   #11, D3
    ADD.B   #20, D4
    TRAP    #15
    
    MOVE.B  #80, D0
    MOVE.L  #PURPLE, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    MOVE.L  PLAYER_X, D3
    MOVE.L  PLAYER_Y, D4
    ADD.B   #4, D1
    ADD.B   #17, D2
    ADD.B   #10, D3
    ADD.B   #19, D4
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    MOVE.L  PLAYER_X, D3
    MOVE.L  PLAYER_Y, D4
    ADD.B   #4, D1
    ADD.B   #15, D2
    ADD.B   #7, D3
    ADD.B   #17, D4
    TRAP    #15
    
    MOVE.B  #80, D0
    MOVE.L  #BLACK, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    MOVE.L  PLAYER_X, D3
    MOVE.L  PLAYER_Y, D4
    ADD.B   #15, D1
    ADD.B   #16, D2
    ADD.B   #18, D3
    ADD.B   #20, D4
    TRAP    #15
    
    MOVE.B  #80, D0
    MOVE.L  #PURPLE, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    MOVE.L  PLAYER_X, D3
    MOVE.L  PLAYER_Y, D4
    ADD.B   #15, D1
    ADD.B   #17, D2
    ADD.B   #17, D3
    ADD.B   #19, D4
    TRAP    #15
    
    MOVE.B  #80, D0
    MOVE.L  #BLUE, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    RTS
    
*-----------------------------------------------------------
* Subroutine    : Draw Canon
* Description   : Draw the canon for the canon ball
*-----------------------------------------------------------
DRAW_CANON: 

    CLR.L   D0
    MOVE.B  #80, D0
    MOVE.L  #GREY, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    ;MAIN BODY
    MOVE.B  #87, D0
    MOVE.W  #632, D1
    MOVE.W  #242, D2
    MOVE.W  #640, D3
    MOVE.W  #257, D4
    TRAP    #15
    
    MOVE.W  #624, D1
    MOVE.W  #244, D2
    MOVE.W  #640, D3
    MOVE.W  #255, D4
    TRAP    #15
    
    ;HIGHLIGHTS
    CLR.L   D0
    MOVE.B  #80, D0
    MOVE.L  #WHITE, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.W  #633, D1
    MOVE.W  #243, D2
    MOVE.W  #640, D3
    MOVE.W  #245, D4
    TRAP    #15
    
    MOVE.W  #625, D1
    MOVE.W  #245, D2
    MOVE.W  #631, D3
    MOVE.W  #247, D4
    TRAP    #15
    
    ;SHADOWS
    CLR.L   D0
    MOVE.B  #80, D0
    MOVE.L  #BLACK, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15
    
    MOVE.B  #87, D0
    MOVE.W  #633, D1
    MOVE.W  #253, D2
    MOVE.W  #640, D3
    MOVE.W  #256, D4
    TRAP    #15
    
    MOVE.W  #624, D1
    MOVE.W  #252, D2
    MOVE.W  #631, D3
    MOVE.W  #255, D4
    TRAP    #15
    
    RTS
    
    
      

*-----------------------------------------------------------
* Subroutine    : Draw Player
* Description   : Check player position to choose animation
*-----------------------------------------------------------
DRAW_PLAYER:
    ; Check if Player is on Ground
    CLR.L   D1                      ; Clear contents of D1 (XOR is faster)
    CLR.L   D2                      ; Clear contents of D2 (XOR is faster)
    MOVE.W  SCREEN_H,   D1          ; Place Screen width in D1
    DIVU    #02,        D1          ; divide by 2 for center on Y Axis
    MOVE.L  PLAYER_Y,   D2          ; Player Y Position
    CMP     D1,         D2          ; Compare middle of Screen with Players Y Position 
    BGE     PLAYER_STAND           ; The Player is on the Ground Plane
    BLT     PLAYER_JUMP         ; The Player is off the Ground
    RTS                             ; Return to subroutine

*-----------------------------------------------------------
* Subroutine    : Draw Enemy
* Description   : Draw Enemy Square
*-----------------------------------------------------------
DRAW_ENEMY:
    ; Set Pixel Colors
    CLR.L   D0
    MOVE.B  #80, D0
    MOVE.L  #DARK_GREY, D1
    TRAP    #15
    MOVE.B  #81, D0
    TRAP    #15

    ; Set X, Y, Width and Height
    MOVE.L  ENEMY_X,    D1          ; X
    MOVE.L  ENEMY_Y,    D2          ; Y
    ADD.B   #6, D2
    MOVE.L  ENEMY_X,    D3
    ADD.L   #ENMY_W_INIT,   D3      ; Width
    MOVE.L  ENEMY_Y,    D4 
    ADD.L   #ENMY_H_INIT,   D4      ; Height
    ADD.B   #6, D4
    
    ; Draw Enemy    
    MOVE.B  #88,        D0          ; Draw Enemy
    TRAP    #15                     ; Trap (Perform action)
    RTS                             ; Return to subroutine
    
*-----------------------------------------------------------
* Subroutine    : Collision Check
* Description   : Axis-Aligned Bounding Box Collision Detection
* Algorithm checks for overlap on the 4 sides of the Player and 
*-----------------------------------------------------------


CHECK_COLLISIONS:
    
    CLR.L   D1
    CLR.L   D2

CHECK_HIT_BOX:
    ;GET COLOR TO CHECK
    MOVE.L  #DARK_GREY, D2
    MOVE.W  D2, D3
    
    ;GET COLOUR OF PIXEL 1 PIXEL AHEAD OF ENEMY
    MOVE.B  #83, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    ADD.B   #5, D1
    ADD.B   #2, D2
    TRAP    #15
    
    ;COMPARE COLOUR VALUES
    MOVE.W  D0, D1
    CMP.W   D1, D3
    BEQ     COLLISION
    
    ;GET COLOUR OF PIXEL 1 PIXEL AHEAD OF ENEMY
    MOVE.B  #83, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    ADD.B   #15, D1
    ADD.B   #2, D2
    TRAP    #15
    
    ;COMPARE COLOUR VALUES
    MOVE.W  D0, D1
    CMP.W   D1, D3
    BEQ     COLLISION
    
    ;GET COLOUR OF PIXEL 1 PIXEL AHEAD OF ENEMY
    MOVE.B  #83, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    ADD.B   #15, D1
    ADD.B   #20, D2
    TRAP    #15
    
    ;COMPARE COLOUR VALUES
    MOVE.W  D0, D1
    CMP.W   D1, D3
    BEQ     COLLISION
    
    ;GET COLOUR OF PIXEL 1 PIXEL AHEAD OF ENEMY
    MOVE.B  #83, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    ADD.B   #2, D1
    ADD.B   #20, D2
    TRAP    #15
    
    ;COMPARE COLOUR VALUES
    MOVE.W  D0, D1
    CMP.W   D1, D3
    BEQ     COLLISION
    
    
    
CHECK_INNER_HIT_BOX:
    
    ;GET COLOR TO CHECK
    MOVE.L  #DARK_GREY, D2
    MOVE.W  D2, D3
    
    ;GET COLOUR OF PIXEL 1 PIXEL AHEAD OF ENEMY
    MOVE.B  #83, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    ADD.B   #9, D1
    ADD.B   #8, D2
    TRAP    #15
    
    ;COMPARE COLOUR VALUES
    MOVE.W  D0, D1
    CMP.W   D1, D3
    BEQ     COLLISION
    
    ;GET COLOUR OF PIXEL 1 PIXEL AHEAD OF ENEMY
    MOVE.B  #83, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    ADD.B   #11, D1
    ADD.B   #8, D2
    TRAP    #15
    
    ;COMPARE COLOUR VALUES
    MOVE.W  D0, D1
    CMP.W   D1, D3
    BEQ     COLLISION
    
    ;GET COLOUR OF PIXEL 1 PIXEL AHEAD OF ENEMY
    MOVE.B  #83, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    ADD.B   #11, D1
    ADD.B   #15, D2
    TRAP    #15
    
    ;COMPARE COLOUR VALUES
    MOVE.W  D0, D1
    CMP.W   D1, D3
    BEQ     COLLISION
    
    ;GET COLOUR OF PIXEL 1 PIXEL AHEAD OF ENEMY
    MOVE.B  #83, D0
    MOVE.L  PLAYER_X, D1
    MOVE.L  PLAYER_Y, D2
    ADD.B   #9, D1
    ADD.B   #15, D2
    TRAP    #15
    
    ;COMPARE COLOUR VALUES
    MOVE.W  D0, D1
    CMP.W   D1, D3
    BEQ     COLLISION
    
    
    
    RTS   
    
    
COLLISION:

    BSR     DEATH_JUMP
    BSR     PLAY_DIE
    BRA     GAME_OVER_LOOP






GAME_OVER_LOOP:
    
    MOVE.L  #0, ENEMY_SPEED
    BSR     UPDATE
    BSR     WAIT_FOR_NEXT_FRAME
    BSR     DRAW_GAME_OVER
    BRA     GAME_OVER_LOOP
    
    
    


*-----------------------------------------------------------
* Subroutine    : EXIT
* Description   : Exit message and End Game
*-----------------------------------------------------------
EXIT:
    ; Show if Exiting is Running
    MOVE.B  #TC_CURSR_P,D0          ; Set Cursor Position
    MOVE.W  #$4004,     D1          ; Col 40, Row 1
    TRAP    #15                     ; Trap (Perform action)
    LEA     EXIT_MSG,   A1          ; Exit
    MOVE    #13,        D0          ; No Line feed
    TRAP    #15                     ; Trap (Perform action)
    MOVE.B  #TC_EXIT,   D0          ; Exit Code
    TRAP    #15                     ; Trap (Perform action)
    SIMHALT

*-----------------------------------------------------------
* Section       : Messages
* Description   : Messages to Print on Console, names should be
* self documenting
*-----------------------------------------------------------
GAME_TITLE      DC.B    'AMONGUS JUMPER', 0                     ; GAME TITLE
JUMP_INST       DC.B    'PRESS SPACE TO JUMP', 0                ; SPACE TO JUMP INSTRUCTION
EXIT_INST       DC.B    'PRESS ESC TO EXIT', 0                  ; ESC TO EXIT INSTRUCTION
START_INST      DC.B    'PRESSING SPACE WILL START THE GAME', 0 ;GAME START INSTRUCTION
GAME_OVER       DC.B    '!!GAME OVER!!', 0                      ;GAME OVER MESSAGE
YOUR_SCORE      DC.B    'YOUR SCORE: ', 0                       ;YOUR SCORE MESSAGE


EXIT_MSG        DC.B    'Exiting....', 0    ; Exit Message
*-----------------------------------------------------------
* Section       : Frame Time Control
* Description   : Holding values for compare functions to determine when frame is updated
*-----------------------------------------------------------
LAST_FRAME_TIME DC.L    $00000000

*-----------------------------------------------------------
* Section       : Graphic Colors
* Description   : Screen Pixel Color
*-----------------------------------------------------------
BLACK           EQU     $00000000
WHITE           EQU     $00FFFFFF
BROWN           EQU     $00043F78
GREEN           EQU     $007DC493
BLUE            EQU     $00E8C59F
PURPLE          EQU     $00EB1575
GREY            EQU     $007A7577
DARK_GREY       EQU     $00292929
OFF_WHITE       EQU     $00E6E6E6

*-----------------------------------------------------------
* Section       : Screen Size
* Description   : Screen Width and Height
*-----------------------------------------------------------
SCREEN_W        DS.W    01  ; Reserve Space for Screen Width
SCREEN_H        DS.W    01  ; Reserve Space for Screen Height

*-----------------------------------------------------------
* Section       : Keyboard Input
* Description   : Used for storing Keypresses
*-----------------------------------------------------------
CURRENT_KEY     DS.L    01  ; Reserve Space for Current Key Pressed

*-----------------------------------------------------------
* Section       : Character Positions
* Description   : Player and Enemy Position Memory Locations
*-----------------------------------------------------------
PLAYER_X        DS.L    01  ; Reserve Space for Player X Position
PLAYER_Y        DS.L    01  ; Reserve Space for Player Y Position
PLAYER_SCORE    DS.L    01  ; Reserve Space for Player Score

PLYR_VELOCITY   DS.L    01  ; Reserve Space for Player Velocity
PLYR_GRAVITY    DS.L    01  ; Reserve Space for Player Gravity
PLYR_ON_GND     DS.L    01  ; Reserve Space for Player on Ground

ENEMY_X         DS.L    01  ; Reserve Space for Enemy X Position
ENEMY_Y         DS.L    01  ; Reserve Space for Enemy Y Position

ENEMY_SPEED     DS.L    01  ; Reserve Space for Enemy Speed


*-----------------------------------------------------------
* Section       : Sounds
* Description   : Sound files, which are then loaded and given
* an address in memory, they take a longtime to process and play
* so keep the files small. Used https://voicemaker.in/ to 
* generate and Audacity to convert MP3 to WAV
*-----------------------------------------------------------
JUMP_WAV        DC.B    'jump.wav',0        ; Jump Sound
SHOT_WAV         DC.B    'shot.wav',0         ; Run Sound
DIE_WAV        DC.B    'DIE.wav',0        ; Collision Opps

    END    START        ; last line of source





*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
