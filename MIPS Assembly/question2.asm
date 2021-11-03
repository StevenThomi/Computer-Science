# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
# Write an assembly program (question2.asm) to compute a simple integer sum.
# Assume that the input sum has no whitespace and that it is suffixed with
# an equals sign ‘=’ and that each element of the sum is an integer in [-9..9].
# Sample input/output:
# Enter a sum: 1+4+5+6-9=
# 7
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#

.data
	prompt:  .asciiz "Enter a sum: \n"
	sum:    .space 100

.text
	main:
		# input prompt
		la $a0, prompt
		li $v0, 4
		syscall

		# input stream
		li $v0, 8
		li $a1, 100
		la $a0, sum
		syscall

		# location of first input memory
		li $t0, 0x1001000e	# address
		li $t2, 0	# sum

		# value of first digit
		lb $a0, ($t0)

		# store in t1
		move $t1, $a0

		Loop:
			# signs
			beq $t1, 0x02d, negative
			beq $t1, 0x03d, equal
			b positive

		positive:

			# signs
			# beq $t1, 0x030, pzero
			beq $t1, 0x031, pone
			beq $t1, 0x032, ptwo
			beq $t1, 0x033, pthree
			beq $t1, 0x034, pfour
			beq $t1, 0x035, pfive
			beq $t1, 0x036, psix
			beq $t1, 0x037, pseven
			beq $t1, 0x038, peight
			beq $t1, 0x039, pnine
			beq $t1, 0x02d, plus

			# get next number if zero
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop

		pone:
			addi $t2, $t2, 1

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		ptwo:
			addi $t2, $t2, 2

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		pthree:
			addi $t2, $t2, 3

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		pfour:
			addi $t2, $t2, 4

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		pfive:
			addi $t2, $t2, 5

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		psix:
			addi $t2, $t2, 6

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		pseven:
			addi $t2, $t2, 7

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		peight:
			addi $t2, $t2, 8

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		pnine:
			addi $t2, $t2, 9

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		plus:
			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j positive
		negative:

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			# signs
			# beq $t1, 0x030, pzero
			beq $t1, 0x031, none
			beq $t1, 0x032, ntwo
			beq $t1, 0x033, nthree
			beq $t1, 0x034, nfour
			beq $t1, 0x035, nfive
			beq $t1, 0x036, nsix
			beq $t1, 0x037, nseven
			beq $t1, 0x038, neight
			beq $t1, 0x039, nnine

			# get next number if zero
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop

		none:
			addi $t2, $t2, -1

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		ntwo:
			addi $t2, $t2, -2

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		nthree:
			addi $t2, $t2, -3

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		nfour:
			addi $t2, $t2, -4

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		nfive:
			addi $t2, $t2, -5

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		nsix:
			addi $t2, $t2, -6

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		nseven:
			addi $t2, $t2, -7

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		neight:
			addi $t2, $t2, -8

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop
		nnine:
			addi $t2, $t2, -9

			# get next number
			addi $t0, $t0, 0x01
			lb $a0, ($t0)

			# store in t1
			move $t1, $a0

			j Loop

		equal:
			# print number
			li $v0, 1
			move $a0, $t2
			syscall

			# end program
			li $v0, 10
			syscall
