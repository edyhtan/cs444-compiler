	global __class_java_io_OutputStream
__class_java_io_OutputStream:

section .data

		global __ref_SIT_java_io_OutputStream
	__ref_SIT_java_io_OutputStream		dd 0

		global __ref_PARENTS_java_io_OutputStream
	__ref_PARENTS_java_io_OutputStream		dd 00000000010000000001b

	; Methods	
		dd __method__java_lang_Object__getClass
		dd __method__java_lang_Object__hashCode
		dd __method__java_lang_Object__equals$java_lang_Object$
		dd __method__java_lang_Object__clone
		dd __method__java_lang_Object__toString
		dd __method__java_io_OutputStream__flush
		dd __STATIC_method__java_io_OutputStream__nativeWrite$int$
		dd __method__java_io_OutputStream__write$int$
		dd __method__java_io_OutputStream__write$char$

section .text

		global __method__java_io_OutputStream__write$char$
	__method__java_io_OutputStream__write$char$:
		push ebp
		mov ebp, esp

		sub esp,0

		_method_return___method__java_io_OutputStream__write$char$:
			pop ebp
			ret

		global __method__java_io_OutputStream__write$int$
	__method__java_io_OutputStream__write$int$:
		push ebp
		mov ebp, esp

		sub esp,0

		_method_return___method__java_io_OutputStream__write$int$:
			pop ebp
			ret

		global __STATIC_method__java_io_OutputStream__nativeWrite$int$
	__STATIC_method__java_io_OutputStream__nativeWrite$int$:
		push ebp
		mov ebp, esp

		sub esp,0

		_method_return___STATIC_method__java_io_OutputStream__nativeWrite$int$:
			pop ebp
			ret

		global __method__java_io_OutputStream__flush
	__method__java_io_OutputStream__flush:
		push ebp
		mov ebp, esp

		sub esp,0

		_method_return___method__java_io_OutputStream__flush:
			pop ebp
			ret
