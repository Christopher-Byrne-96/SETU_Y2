*-----------------------------------------------------------
* Title      : Task 2
* Written by : Christopher Byrne
* Date       : 25/01/2023
* Description: perform arithmatic functions on Health 
*-----------------------------------------------------------
    ORG    $1000
START:                  ; first instruction of program

* Put program code here

    LEA Health, A2
    MOVE    #3, D0
    MOVE.B  (A2), D1
    TRAP    #15
    MOVE.B  #5, D2
    ADD     D2, D1
    TRAP    #15
    
    SUB D2, D1
    TRAP    #15
    
    MOVE.B  #2, D2
    MULS.W  D2, D1
    TRAP    #15
    
    DIVS.W  D2, D1
    TRAP    #15

    SIMHALT             ; halt simulator

* Put variables and constants here

Health  dc.b    $10

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
