*-----------------------------------------------------------
* Title      : Part 2
* Written by : Christopher Byrne
* Date       : 11/01/2023
* Description: Example of input output wiht Trap #15
*-----------------------------------------------------------
    ORG    $1000
START:                  ; first instruction of program

* Put program code here
    MOVE.B #9,D1        
    LEA text, A1        ; Puts the memory location of text int A1
    MOVE #14,D0         ; 14 in D0 tells the TRAP #15 function to display whats in A1
    TRAP #15            ; Command used for I/O
    
    MOVE #3,D0          ; 3 tells the TRAP #15 function to display number stored in D1
    TRAP #15

    SIMHALT             ; halt simulator

* Put variables and constants here
text dc.b 'Data Register: ',0

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
