	global __class_java_lang_Number
__class_java_lang_Number:
section .data

		global __ref_SIT_java_lang_Number
	__ref_SIT_java_lang_Number		dd 0

		global __ref_PARENTS_java_lang_Number
	__ref_PARENTS_java_lang_Number		dd 0

		global __method__java_lang_Number__getClass
	__method__java_lang_Number__getClass:
		dd [__method__java_lang_Object__getClass]

		global __method__java_lang_Number__hashCode
	__method__java_lang_Number__hashCode:
		dd [__method__java_lang_Object__hashCode]

		global __method__java_lang_Number__equals$java_lang_Object$
	__method__java_lang_Number__equals$java_lang_Object$:
		dd [__method__java_lang_Object__equals$java_lang_Object$]

		global __method__java_lang_Number__clone
	__method__java_lang_Number__clone:
		dd [__method__java_lang_Object__clone]

		global __method__java_lang_Number__toString
	__method__java_lang_Number__toString:
		dd [__method__java_lang_Object__toString]

		global __method__java_lang_Number__intValue
	__method__java_lang_Number__intValue:
		dd __m_5

section .text

	__m_5:
		push ebp
		mov ebp, esp

		sub esp,0

		_method_return___m_5:
			pop ebp
			ret

