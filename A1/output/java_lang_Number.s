	global __class_java_lang_Number
__class_java_lang_Number:

section .data

		global __ref_SIT_java_lang_Number
	__ref_SIT_java_lang_Number		dd 0

		global __ref_PARENTS_java_lang_Number
	__ref_PARENTS_java_lang_Number		dd 00000001001000000000b

	; Methods	
		dd __method__java_lang_Object__getClass
		dd __method__java_lang_Object__hashCode
		dd __method__java_lang_Object__equals$java_lang_Object$
		dd __method__java_lang_Object__clone
		dd __method__java_lang_Object__toString
		dd __method__java_lang_Number__intValue

section .text

-----Methods-----
		global __method__java_lang_Number__intValue
	__method__java_lang_Number__intValue:
		push ebp
		mov ebp, esp

		
		_method_return___method__java_lang_Number__intValue:
			pop ebp
			ret

;; -----Constructors-----
		extern __constructor__java_lang_Number__Number
	__constructor__java_lang_Number__Number:
		push ebp
		mov ebp, esp
		mov eax, [ebp + 8]
		push eax
		mov eax, __constuctor__java_lang_Object__Object
		call eax
		sub esp,4
;; Field init, push object to stack
		mov eax, [ebp + 8]
		push eax
;; Field init end, pop object
		add esp,4
;; Constructor Body
;; Epilogue
		mov esp, ebp
		pop ebp
		ret
