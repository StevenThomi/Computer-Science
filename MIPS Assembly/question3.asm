# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
# Write a program (question3.asm) to complete rotations
# (clockwise and anti-clockwise) of a 3 x 3 character matrix. The program
# must the receive the character matrix line by line
# (where each line is a 3 element row) followed by an integer for the number
# of rotations, where positive values correspond to clockwise rotations and
# negative values correspond to anti-clockwise rotations. Your program must
# output the final rotated matrix.
# Sample input/output:
# Enter a 3x3 matrix:
# abc
# def
# hij
# -3
# hda
# ieb
# jfc
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#

.data
	prompt:   .asciiz "Enter a 3x3 matrix: \n"
	nextline: .asciiz "\n"
	row1:     .space 4 # "abc"
	row2:     .space 4 # "def"
	row3:     .space 4 # "ghi"

.text
main:
	# input prompt
	la $a0, prompt
	li $v0, 4
	syscall

	# input stream
	li $v0, 8
	li $a1, 5

	la $a0, row1
	syscall

	la $a0, row2
	syscall

	la $a0, row3
	syscall

	# number and orientation of rotations
	li $v0, 5
	syscall

	move $t0, $v0

	# li $v0, 1
	# move $a0, $t0
	# syscall

	# 3x3 matrix data input, element-by-element
	lb $t1, 0x10010018
	lb $t2, 0x10010019
	lb $t3, 0x1001001a
	lb $t4, 0x1001001c
	lb $t5, 0x1001001d
	lb $t6, 0x1001001e
	lb $t7, 0x10010020
	lb $s0, 0x10010021
	lb $s1, 0x10010022

	# branch conditions
	bgt $t0, $zero, clockwise
	blt $t0, $zero, anticlockwise
	beq $t0, $zero, printmatrix

	# clockwise rotation
clockwise:
	beq $t0, $zero, printmatrix		# monitor condition

	# s2 introduced, temporary variable for swap
	# starting with edges,...
	move $s2, $t3
	move $t3, $t1
	move $t1, $t7
	move $t7, $s1
	move $s1, $s2

	# ... then between edges
	move $s2, $t6
	move $t6, $t2
	move $t2, $t4
	move $t4, $s0
	move $s0, $s2

	addi $t0, $t0, -1

	j clockwise

# anti-clockwise rotation
anticlockwise:
	beq $t0, $zero, printmatrix		# monitor condition

	# s2 introduced, temporary variable for swap
	# starting with edges,...
	move $s2, $t3
	move $t3, $s1
	move $s1, $t7
	move $t7, $t1
	move $t1, $s2

	# ... then between edges
	move $s2, $t6
	move $t6, $s0
	move $s0, $t4
	move $t4, $t2
	move $t2, $s2

	addi $t0, $t0, 1

	j anticlockwise

# when no more rotations are needed, print output in 3x3 grid
printmatrix:

	li $v0, 11
	move $a0, $t1
	syscall

	move $a0, $t2
	syscall

	move $a0, $t3
	syscall

	li $v0, 4
	la $a0, nextline
	syscall

	li $v0, 11
	move $a0, $t4
	syscall

	move $a0, $t5
	syscall

	move $a0, $t6
	syscall

	li $v0, 4
	la $a0, nextline
	syscall

	li $v0, 11
	move $a0, $t7
	syscall

	move $a0, $s0
	syscall

	move $a0, $s1
	syscall

	# end program
	li $v0, 10
	syscall
