# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
# Write a simple assembly program (question1.asm) that receives three
# separate lines of input, in the order 0-1-2, and reorders them according to
# the following sequence: 2-0-1. You should store each line in memory.
# Sample input/output:
# Enter a list of 3 lines:
# One
# Two
# Three
# The reordered list is:
# Three
# One
# Two
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#

.data
	prompt1:  .asciiz "Enter a list of 3 lines: \n"
	prompt2:  .asciiz "The reordered list is: \n"
	first:    .space 20
	second:   .space 20
	third:    .space 20

.text
	main:
		# input prompt
		la $a0, prompt1
		li $v0, 4
		syscall

		# input stream
		li $v0, 8
		li $a1, 20

		# read first line
		la $a0, first
		syscall

		# read second line
		la $a0, second
		syscall

		# read third line
		la $a0, third
		syscall

		# output prompt
		la $a0, prompt2
		li $v0, 4
		syscall

		# print third (and next line)
		la $a0, third
		syscall

		# print first (and next line)
		la $a0, first
		syscall

		# print second (and next line)
		la $a0, second
		syscall

	# end program
	li $v0, 10
	syscall
