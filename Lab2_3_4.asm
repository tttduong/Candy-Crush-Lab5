		.data		# the data segment
prompt: .asciiz "Guess a number (1 - 1000): "
win: 	.asciiz	"You win!!\n"
lose: 	.asciiz	"You lose!!, try again \n"
newline:.asciiz	"\n"




		.text		# the code segment
		.globl main
main:
	
	li $t0, 0x1fa
TYPE: 
	# print out the prompt
	la $a0, prompt		
	li $v0, 4		
	syscall
	
	# read in an integer
	li $v0, 5			
	syscall
	move $t1, $v0
	
CHECK: 
	beq $t1, $t0, WIN 
	bne $t1, $t0, CASE 
CASE: 
	la $a0, lose 
	li $v0, 4 
	syscall 
	j TYPE
WIN: 
	la $a0, win 
	li $v0, 4 
	syscall 
	j EXIT 
	
EXIT: 
	jr $ra 
	
	
 
	
	


	
