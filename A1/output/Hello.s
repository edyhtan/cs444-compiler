extern __method__java_lang_Object__clone
extern __method__java_lang_Object__toString
extern __constructor__java_lang_Object__Object
extern __method__java_lang_Object__hashCode
extern __method__java_lang_Object__getClass
extern __method__java_lang_Object__equals$java_lang_Object$


section .data

	global __class_Hello
__class_Hello:
		global __ref_SIT_Hello
	__ref_SIT_Hello:
		dd 0

		global __ref_PARENTS_Hello
	__ref_PARENTS_Hello:
		dd 001100000010000000000b

	; Methods	
		dd __method__java_lang_Object__getClass
		dd __method__java_lang_Object__hashCode
		dd __method__java_lang_Object__equals$java_lang_Object$
		dd __method__java_lang_Object__clone
		dd __method__java_lang_Object__toString
		dd __method__Hello__happyHours

;; Static fields
		global __field_Hello_staticInt
__field_Hello_staticInt:
		dd 0


section .text

;; -----Methods-----
		global __method__Hello__happyHours
	__method__Hello__happyHours:
		push ebp
		mov ebp, esp

		mov eax, 3
		jmp _method_return___method__Hello__happyHours

		_method_return___method__Hello__happyHours:
			mov esp, ebp
			pop ebp
			ret

;; -----Constructors-----
		global __constructor__Hello__Hello
	__constructor__Hello__Hello:
		push ebp
		mov ebp, esp
		mov eax, [ebp + 8]
		push eax
					mov eax, __constructor__java_lang_Object__Object
		call eax
		sub esp, 4
;; Field init, push object to stack
		mov eax, [ebp + 8]
		push eax
;; Field init:: k
				mov eax, 0
		mov ebx, [esp]
		add ebx, 4
		mov [ebx], eax
;; Field init end, pop object
		add esp, 4
;; Constructor Body
;; Epilogue
		mov esp, ebp
		pop ebp
		ret
		global __constructor__Hello__Hello$int$
	__constructor__Hello__Hello$int$:
		push ebp
		mov ebp, esp
		mov eax, [ebp + 12]
		push eax
					mov eax, __constructor__java_lang_Object__Object
		call eax
		sub esp, 4
;; Field init, push object to stack
		mov eax, [ebp + 12]
		push eax
;; Field init:: k
				mov eax, 0
		mov ebx, [esp]
		add ebx, 4
		mov [ebx], eax
;; Field init end, pop object
		add esp, 4
;; Constructor Body
;; Epilogue
		mov esp, ebp
		pop ebp
		ret
