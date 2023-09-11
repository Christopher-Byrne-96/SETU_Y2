*-----------------------------------------------------------
* Title      : PART 1 
* Written by : CHRISTOPHER BYRNE
* Date       : 11/01/2023
* Description: MOVING DATA FROM REGISTERS TO MEMORY 
*-----------------------------------------------------------
    ORG    $1000
START:                  ; first instruction of program

* Put program code here

    MOVE.B  #9,D1       ;MOVE LITERAL VALUE 9 INTO DATA REGISTER D1 
    MOVE.B  D1,D2       ;MOVE THE VALUE STORED IN D1 TO D2 
    MOVE.B  D1,1100     ;MOVE THE VALUE IN D1 TO 1100 WHICH IS MEMORY LOCATION 044C
    MOVE.B  1100,D2     ;MOVE VALUE IN 044C T0 0064
    MOVE.B  1100,100    ;MEMORY LOCATION FOR 100 IS 0064 CONTENTS ARE 09 IN HEX

    SIMHALT             ; halt simulator

* Put variables and constants here

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
