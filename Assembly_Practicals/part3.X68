*-----------------------------------------------------------
* Title      : Part 3
* Written by : Christopher Byrne
* Date       : 11/01/2023
* Description: Example of input output wiht Trap #15
*-----------------------------------------------------------
    ORG    $1000
START:                  ; first instruction of program

* Put program code here
    LEA text, A1        ;Puts the address of the text into A1
    MOVE #4,D0          ;Move 4 into D0 tells the TRAP #15 to read a number from the keyboard and store it in D1
    TRAP #15
    MOVE #14,D0         ;MOVE 14 into D0 tells the TRAP #15 to display the text from A1
    TRAP #15
    MOVE #3,D0          ;MOVE 3 into D) tells trap #15 to display the number in decimal that is store in D1
    TRAP #15  

    SIMHALT             ; halt simulator

* Put variables and constants here
text dc.b 'Data Register: ',0

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
