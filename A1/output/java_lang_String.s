	global __class_java_lang_String
__class_java_lang_String:

section .data

		global __ref_SIT_java_lang_String
	__ref_SIT_java_lang_String		dd 0

		global __ref_PARENTS_java_lang_String
	__ref_PARENTS_java_lang_String		dd 00000001010000000000b

	; Methods	
		dd __method__java_lang_Object__getClass
		dd __method__java_lang_Object__hashCode
		dd __method__java_lang_Object__equals$java_lang_Object$
		dd __method__java_lang_Object__clone
		dd __method__java_lang_Object__toString
		dd __STATIC_method__java_lang_String__valueOf$char$
		dd __method__java_lang_String__toCharArray
		dd __method__java_lang_String__indexOf$java_lang_String$
		dd __method__java_lang_String__charAt$int$
		dd __method__java_lang_String__compareTo$java_lang_String$
		dd __method__java_lang_String__length
		dd __STATIC_method__java_lang_String__valueOf$java_lang_Object$
		dd __STATIC_method__java_lang_String__valueOf$short$
		dd __method__java_lang_String__concat$java_lang_String$
		dd __method__java_lang_String__substring$int$int$
		dd __STATIC_method__java_lang_String__valueOf$byte$
		dd __method__java_lang_String__trim
		dd __STATIC_method__java_lang_String__valueOf$int$
		dd __STATIC_method__java_lang_String__valueOf$java_lang_String$
		dd __STATIC_method__java_lang_String__valueOf$boolean$
		dd __method__java_lang_String__compareTo$java_lang_Object$

section .text

		global __method__java_lang_String__length
	__method__java_lang_String__length:
		push ebp
		mov ebp, esp

		sub esp,0
		jmp _method_return___method__java_lang_String__length

		_method_return___method__java_lang_String__length:
			pop ebp
			ret

		global __method__java_lang_String__charAt$int$
	__method__java_lang_String__charAt$int$:
		push ebp
		mov ebp, esp

		sub esp,0
		jmp _method_return___method__java_lang_String__charAt$int$

		_method_return___method__java_lang_String__charAt$int$:
			pop ebp
			ret

		global __method__java_lang_String__concat$java_lang_String$
	__method__java_lang_String__concat$java_lang_String$:
		push ebp
		mov ebp, esp

		sub esp,8
		mov eax, 0
		mov [ebp-4], eax
		mov [ebp-8], eax
		jmp _method_return___method__java_lang_String__concat$java_lang_String$

		_method_return___method__java_lang_String__concat$java_lang_String$:
			pop ebp
			ret

		global __STATIC_method__java_lang_String__valueOf$char$
	__STATIC_method__java_lang_String__valueOf$char$:
		push ebp
		mov ebp, esp

		sub esp,4
		mov [ebp-4], eax
		jmp _method_return___STATIC_method__java_lang_String__valueOf$char$

		_method_return___STATIC_method__java_lang_String__valueOf$char$:
			pop ebp
			ret

		global __STATIC_method__java_lang_String__valueOf$int$
	__STATIC_method__java_lang_String__valueOf$int$:
		push ebp
		mov ebp, esp

		sub esp,16
		mov [ebp-4], eax
		mov eax, 0
		mov [ebp-8], eax
		mov eax, 0
		mov [ebp-12], eax
		;expression code...
		cmp eax,0
		je .else2
		;thenClause ...
						jmp _method_return___STATIC_method__java_lang_String__valueOf$int$

		jmp .endif2

		.else2:
		.endif2:
		;expression code...
		cmp eax,0
		je .else3
		;thenClause ...
			
		jmp .endif3

		.else3:
		.endif3:
		;expression code...
		cmp eax,0
		je .else4
		;thenClause ...
			
		jmp .endif4

		.else4:
			;elseClause ...
			.while5:
				;expression code...
				cmp eax,0
				je .endwhile5

				;statement code...
					mov [ebp0], eax
										add esp,4


				jmp .while5

			.endwhile5:

			add esp,4

			
		.endif4:
		;expression code...
		cmp eax,0
		je .else6
		;thenClause ...
			
		jmp .endif6

		.else6:
		.endif6:
		mov [ebp-16], eax
		jmp _method_return___STATIC_method__java_lang_String__valueOf$int$

		_method_return___STATIC_method__java_lang_String__valueOf$int$:
			pop ebp
			ret

		global __STATIC_method__java_lang_String__valueOf$short$
	__STATIC_method__java_lang_String__valueOf$short$:
		push ebp
		mov ebp, esp

		sub esp,0
		jmp _method_return___STATIC_method__java_lang_String__valueOf$short$

		_method_return___STATIC_method__java_lang_String__valueOf$short$:
			pop ebp
			ret

		global __STATIC_method__java_lang_String__valueOf$byte$
	__STATIC_method__java_lang_String__valueOf$byte$:
		push ebp
		mov ebp, esp

		sub esp,0
		jmp _method_return___STATIC_method__java_lang_String__valueOf$byte$

		_method_return___STATIC_method__java_lang_String__valueOf$byte$:
			pop ebp
			ret

		global __STATIC_method__java_lang_String__valueOf$boolean$
	__STATIC_method__java_lang_String__valueOf$boolean$:
		push ebp
		mov ebp, esp

		sub esp,0
		;expression code...
		cmp eax,0
		je .else8
		;thenClause ...
						jmp _method_return___STATIC_method__java_lang_String__valueOf$boolean$

		jmp .endif8

		.else8:
			;elseClause ...
						jmp _method_return___STATIC_method__java_lang_String__valueOf$boolean$

		.endif8:

		_method_return___STATIC_method__java_lang_String__valueOf$boolean$:
			pop ebp
			ret

		global __STATIC_method__java_lang_String__valueOf$java_lang_Object$
	__STATIC_method__java_lang_String__valueOf$java_lang_Object$:
		push ebp
		mov ebp, esp

		sub esp,0
		;expression code...
		cmp eax,0
		je .else9
		;thenClause ...
						jmp _method_return___STATIC_method__java_lang_String__valueOf$java_lang_Object$

		jmp .endif9

		.else9:
			;elseClause ...
			jmp _method_return___STATIC_method__java_lang_String__valueOf$java_lang_Object$

		.endif9:

		_method_return___STATIC_method__java_lang_String__valueOf$java_lang_Object$:
			pop ebp
			ret

		global __STATIC_method__java_lang_String__valueOf$java_lang_String$
	__STATIC_method__java_lang_String__valueOf$java_lang_String$:
		push ebp
		mov ebp, esp

		sub esp,0
		;expression code...
		cmp eax,0
		je .else10
		;thenClause ...
						jmp _method_return___STATIC_method__java_lang_String__valueOf$java_lang_String$

		jmp .endif10

		.else10:
			;elseClause ...
			jmp _method_return___STATIC_method__java_lang_String__valueOf$java_lang_String$

		.endif10:

		_method_return___STATIC_method__java_lang_String__valueOf$java_lang_String$:
			pop ebp
			ret

		global __method__java_lang_String__equals$java_lang_Object$
	__method__java_lang_String__equals$java_lang_Object$:
		push ebp
		mov ebp, esp

		sub esp,0
		;expression code...
		cmp eax,0
		je .else11
		;thenClause ...
			mov eax, 0
			jmp _method_return___method__java_lang_String__equals$java_lang_Object$

		jmp .endif11

		.else11:
		.endif11:
		;expression code...
		cmp eax,0
		je .else12
		;thenClause ...
			mov eax, 0
			jmp _method_return___method__java_lang_String__equals$java_lang_Object$

		jmp .endif12

		.else12:
		.endif12:
		jmp _method_return___method__java_lang_String__equals$java_lang_Object$

		_method_return___method__java_lang_String__equals$java_lang_Object$:
			pop ebp
			ret

		global __method__java_lang_String__substring$int$int$
	__method__java_lang_String__substring$int$int$:
		push ebp
		mov ebp, esp

		sub esp,8
		mov eax, 0
		mov [ebp-4], eax
		;expression code...
		cmp eax,0
		je .else13
		;thenClause ...
						jmp _method_return___method__java_lang_String__substring$int$int$

		jmp .endif13

		.else13:
		.endif13:
		;expression code...
		cmp eax,0
		je .else14
		;thenClause ...
						jmp _method_return___method__java_lang_String__substring$int$int$

		jmp .endif14

		.else14:
		.endif14:
		;expression code...
		cmp eax,0
		je .else15
		;thenClause ...
						jmp _method_return___method__java_lang_String__substring$int$int$

		jmp .endif15

		.else15:
		.endif15:
		mov [ebp-8], eax
		jmp _method_return___method__java_lang_String__substring$int$int$

		_method_return___method__java_lang_String__substring$int$int$:
			pop ebp
			ret

		global __method__java_lang_String__trim
	__method__java_lang_String__trim:
		push ebp
		mov ebp, esp

		sub esp,8
		mov eax, 0
		mov [ebp-4], eax
		mov eax, 0
		mov [ebp-8], eax
		;expression code...
		cmp eax,0
		je .else19
		;thenClause ...
						jmp _method_return___method__java_lang_String__trim

		jmp .endif19

		.else19:
			;elseClause ...
			jmp _method_return___method__java_lang_String__trim

		.endif19:

		_method_return___method__java_lang_String__trim:
			pop ebp
			ret

		global __method__java_lang_String__hashCode
	__method__java_lang_String__hashCode:
		push ebp
		mov ebp, esp

		sub esp,4
		mov eax, 0
		mov [ebp-4], eax
		add esp,4
		jmp _method_return___method__java_lang_String__hashCode

		_method_return___method__java_lang_String__hashCode:
			pop ebp
			ret

		global __method__java_lang_String__toString
	__method__java_lang_String__toString:
		push ebp
		mov ebp, esp

		sub esp,0
		jmp _method_return___method__java_lang_String__toString

		_method_return___method__java_lang_String__toString:
			pop ebp
			ret

		global __method__java_lang_String__compareTo$java_lang_Object$
	__method__java_lang_String__compareTo$java_lang_Object$:
		push ebp
		mov ebp, esp

		sub esp,0
		jmp _method_return___method__java_lang_String__compareTo$java_lang_Object$

		_method_return___method__java_lang_String__compareTo$java_lang_Object$:
			pop ebp
			ret

		global __method__java_lang_String__compareTo$java_lang_String$
	__method__java_lang_String__compareTo$java_lang_String$:
		push ebp
		mov ebp, esp

		sub esp,8
		mov eax, 0
		mov [ebp-4], eax
		mov eax, 1
		mov [ebp-8], eax
		.while21:
			;expression code...
			cmp eax,0
			je .endwhile21

			;statement code...
				;expression code...
				cmp eax,0
				je .else22
				;thenClause ...
					mov eax, 0
					jmp _method_return___method__java_lang_String__compareTo$java_lang_String$

				jmp .endif22

				.else22:
				.endif22:
				;expression code...
				cmp eax,0
				je .else23
				;thenClause ...
					jmp _method_return___method__java_lang_String__compareTo$java_lang_String$

				jmp .endif23

				.else23:
				.endif23:
				;expression code...
				cmp eax,0
				je .else24
				;thenClause ...
					mov eax, 1
					jmp _method_return___method__java_lang_String__compareTo$java_lang_String$

				jmp .endif24

				.else24:
				.endif24:
				;expression code...
				cmp eax,0
				je .else25
				;thenClause ...
					jmp _method_return___method__java_lang_String__compareTo$java_lang_String$

				jmp .endif25

				.else25:
				.endif25:
				;expression code...
				cmp eax,0
				je .else26
				;thenClause ...
					mov eax, 1
					jmp _method_return___method__java_lang_String__compareTo$java_lang_String$

				jmp .endif26

				.else26:
				.endif26:
				
			jmp .while21

		.endwhile21:

		mov eax, 0
		jmp _method_return___method__java_lang_String__compareTo$java_lang_String$

		_method_return___method__java_lang_String__compareTo$java_lang_String$:
			pop ebp
			ret

		global __method__java_lang_String__toCharArray
	__method__java_lang_String__toCharArray:
		push ebp
		mov ebp, esp

		sub esp,4
		mov [ebp-4], eax
		add esp,4
		jmp _method_return___method__java_lang_String__toCharArray

		_method_return___method__java_lang_String__toCharArray:
			pop ebp
			ret

		global __method__java_lang_String__indexOf$java_lang_String$
	__method__java_lang_String__indexOf$java_lang_String$:
		push ebp
		mov ebp, esp

		sub esp,8
		mov eax, 0
		mov [ebp-4], eax
		mov eax, 0
		mov [ebp-8], eax
		jmp _method_return___method__java_lang_String__indexOf$java_lang_String$

		_method_return___method__java_lang_String__indexOf$java_lang_String$:
			pop ebp
			ret
