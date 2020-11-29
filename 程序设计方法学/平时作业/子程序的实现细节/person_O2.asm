
a.out:     file format elf64-x86-64


Disassembly of section .init:

0000000000001000 <_init>:
    1000:	f3 0f 1e fa          	endbr64 
    1004:	48 83 ec 08          	sub    $0x8,%rsp
    1008:	48 8b 05 d9 2f 00 00 	mov    0x2fd9(%rip),%rax        # 3fe8 <__gmon_start__>
    100f:	48 85 c0             	test   %rax,%rax
    1012:	74 02                	je     1016 <_init+0x16>
    1014:	ff d0                	callq  *%rax
    1016:	48 83 c4 08          	add    $0x8,%rsp
    101a:	c3                   	retq   

Disassembly of section .plt:

0000000000001020 <.plt>:
    1020:	ff 35 a2 2f 00 00    	pushq  0x2fa2(%rip)        # 3fc8 <_GLOBAL_OFFSET_TABLE_+0x8>
    1026:	f2 ff 25 a3 2f 00 00 	bnd jmpq *0x2fa3(%rip)        # 3fd0 <_GLOBAL_OFFSET_TABLE_+0x10>
    102d:	0f 1f 00             	nopl   (%rax)

Disassembly of section .plt.got:

0000000000001030 <__cxa_finalize@plt>:
    1030:	f3 0f 1e fa          	endbr64 
    1034:	f2 ff 25 bd 2f 00 00 	bnd jmpq *0x2fbd(%rip)        # 3ff8 <__cxa_finalize@GLIBC_2.2.5>
    103b:	0f 1f 44 00 00       	nopl   0x0(%rax,%rax,1)

Disassembly of section .text:

0000000000001040 <main>:
    1040:	f3 0f 1e fa          	endbr64 
    1044:	31 c0                	xor    %eax,%eax
    1046:	c3                   	retq   
    1047:	66 0f 1f 84 00 00 00 	nopw   0x0(%rax,%rax,1)
    104e:	00 00 

0000000000001050 <_start>:
    1050:	f3 0f 1e fa          	endbr64 
    1054:	31 ed                	xor    %ebp,%ebp
    1056:	49 89 d1             	mov    %rdx,%r9
    1059:	5e                   	pop    %rsi
    105a:	48 89 e2             	mov    %rsp,%rdx
    105d:	48 83 e4 f0          	and    $0xfffffffffffffff0,%rsp
    1061:	50                   	push   %rax
    1062:	54                   	push   %rsp
    1063:	4c 8d 05 b6 01 00 00 	lea    0x1b6(%rip),%r8        # 1220 <__libc_csu_fini>
    106a:	48 8d 0d 3f 01 00 00 	lea    0x13f(%rip),%rcx        # 11b0 <__libc_csu_init>
    1071:	48 8d 3d c8 ff ff ff 	lea    -0x38(%rip),%rdi        # 1040 <main>
    1078:	ff 15 62 2f 00 00    	callq  *0x2f62(%rip)        # 3fe0 <__libc_start_main@GLIBC_2.2.5>
    107e:	f4                   	hlt    
    107f:	90                   	nop

0000000000001080 <deregister_tm_clones>:
    1080:	48 8d 3d 89 2f 00 00 	lea    0x2f89(%rip),%rdi        # 4010 <__TMC_END__>
    1087:	48 8d 05 82 2f 00 00 	lea    0x2f82(%rip),%rax        # 4010 <__TMC_END__>
    108e:	48 39 f8             	cmp    %rdi,%rax
    1091:	74 15                	je     10a8 <deregister_tm_clones+0x28>
    1093:	48 8b 05 3e 2f 00 00 	mov    0x2f3e(%rip),%rax        # 3fd8 <_ITM_deregisterTMCloneTable>
    109a:	48 85 c0             	test   %rax,%rax
    109d:	74 09                	je     10a8 <deregister_tm_clones+0x28>
    109f:	ff e0                	jmpq   *%rax
    10a1:	0f 1f 80 00 00 00 00 	nopl   0x0(%rax)
    10a8:	c3                   	retq   
    10a9:	0f 1f 80 00 00 00 00 	nopl   0x0(%rax)

00000000000010b0 <register_tm_clones>:
    10b0:	48 8d 3d 59 2f 00 00 	lea    0x2f59(%rip),%rdi        # 4010 <__TMC_END__>
    10b7:	48 8d 35 52 2f 00 00 	lea    0x2f52(%rip),%rsi        # 4010 <__TMC_END__>
    10be:	48 29 fe             	sub    %rdi,%rsi
    10c1:	48 89 f0             	mov    %rsi,%rax
    10c4:	48 c1 ee 3f          	shr    $0x3f,%rsi
    10c8:	48 c1 f8 03          	sar    $0x3,%rax
    10cc:	48 01 c6             	add    %rax,%rsi
    10cf:	48 d1 fe             	sar    %rsi
    10d2:	74 14                	je     10e8 <register_tm_clones+0x38>
    10d4:	48 8b 05 15 2f 00 00 	mov    0x2f15(%rip),%rax        # 3ff0 <_ITM_registerTMCloneTable>
    10db:	48 85 c0             	test   %rax,%rax
    10de:	74 08                	je     10e8 <register_tm_clones+0x38>
    10e0:	ff e0                	jmpq   *%rax
    10e2:	66 0f 1f 44 00 00    	nopw   0x0(%rax,%rax,1)
    10e8:	c3                   	retq   
    10e9:	0f 1f 80 00 00 00 00 	nopl   0x0(%rax)

00000000000010f0 <__do_global_dtors_aux>:
    10f0:	f3 0f 1e fa          	endbr64 
    10f4:	80 3d 15 2f 00 00 00 	cmpb   $0x0,0x2f15(%rip)        # 4010 <__TMC_END__>
    10fb:	75 2b                	jne    1128 <__do_global_dtors_aux+0x38>
    10fd:	55                   	push   %rbp
    10fe:	48 83 3d f2 2e 00 00 	cmpq   $0x0,0x2ef2(%rip)        # 3ff8 <__cxa_finalize@GLIBC_2.2.5>
    1105:	00 
    1106:	48 89 e5             	mov    %rsp,%rbp
    1109:	74 0c                	je     1117 <__do_global_dtors_aux+0x27>
    110b:	48 8b 3d f6 2e 00 00 	mov    0x2ef6(%rip),%rdi        # 4008 <__dso_handle>
    1112:	e8 19 ff ff ff       	callq  1030 <__cxa_finalize@plt>
    1117:	e8 64 ff ff ff       	callq  1080 <deregister_tm_clones>
    111c:	c6 05 ed 2e 00 00 01 	movb   $0x1,0x2eed(%rip)        # 4010 <__TMC_END__>
    1123:	5d                   	pop    %rbp
    1124:	c3                   	retq   
    1125:	0f 1f 00             	nopl   (%rax)
    1128:	c3                   	retq   
    1129:	0f 1f 80 00 00 00 00 	nopl   0x0(%rax)

0000000000001130 <frame_dummy>:
    1130:	f3 0f 1e fa          	endbr64 
    1134:	e9 77 ff ff ff       	jmpq   10b0 <register_tm_clones>
    1139:	0f 1f 80 00 00 00 00 	nopl   0x0(%rax)

0000000000001140 <create>:
    1140:	f3 0f 1e fa          	endbr64 
    1144:	48 89 f8             	mov    %rdi,%rax
    1147:	85 f6                	test   %esi,%esi
    1149:	74 4d                	je     1198 <create+0x58>
    114b:	83 fe 01             	cmp    $0x1,%esi
    114e:	b9 64 00 00 00       	mov    $0x64,%ecx
    1153:	ba 00 00 00 00       	mov    $0x0,%edx
    1158:	bf aa 00 00 00       	mov    $0xaa,%edi
    115d:	0f 44 d1             	cmove  %ecx,%edx
    1160:	b9 00 00 00 00       	mov    $0x0,%ecx
    1165:	41 b9 4c 00 00 00    	mov    $0x4c,%r9d
    116b:	41 b8 00 00 00 00    	mov    $0x0,%r8d
    1171:	0f 44 cf             	cmove  %edi,%ecx
    1174:	bf 00 00 00 00       	mov    $0x0,%edi
    1179:	41 0f 44 f9          	cmove  %r9d,%edi
    117d:	c7 00 14 00 00 00    	movl   $0x14,(%rax)
    1183:	40 88 78 04          	mov    %dil,0x4(%rax)
    1187:	44 89 40 0c          	mov    %r8d,0xc(%rax)
    118b:	89 70 10             	mov    %esi,0x10(%rax)
    118e:	89 48 14             	mov    %ecx,0x14(%rax)
    1191:	89 50 18             	mov    %edx,0x18(%rax)
    1194:	c3                   	retq   
    1195:	0f 1f 00             	nopl   (%rax)
    1198:	ba 78 00 00 00       	mov    $0x78,%edx
    119d:	b9 b4 00 00 00       	mov    $0xb4,%ecx
    11a2:	41 b8 01 00 00 00    	mov    $0x1,%r8d
    11a8:	bf 54 00 00 00       	mov    $0x54,%edi
    11ad:	eb ce                	jmp    117d <create+0x3d>
    11af:	90                   	nop

00000000000011b0 <__libc_csu_init>:
    11b0:	f3 0f 1e fa          	endbr64 
    11b4:	41 57                	push   %r15
    11b6:	4c 8d 3d 33 2c 00 00 	lea    0x2c33(%rip),%r15        # 3df0 <__frame_dummy_init_array_entry>
    11bd:	41 56                	push   %r14
    11bf:	49 89 d6             	mov    %rdx,%r14
    11c2:	41 55                	push   %r13
    11c4:	49 89 f5             	mov    %rsi,%r13
    11c7:	41 54                	push   %r12
    11c9:	41 89 fc             	mov    %edi,%r12d
    11cc:	55                   	push   %rbp
    11cd:	48 8d 2d 24 2c 00 00 	lea    0x2c24(%rip),%rbp        # 3df8 <__init_array_end>
    11d4:	53                   	push   %rbx
    11d5:	4c 29 fd             	sub    %r15,%rbp
    11d8:	48 83 ec 08          	sub    $0x8,%rsp
    11dc:	e8 1f fe ff ff       	callq  1000 <_init>
    11e1:	48 c1 fd 03          	sar    $0x3,%rbp
    11e5:	74 1f                	je     1206 <__libc_csu_init+0x56>
    11e7:	31 db                	xor    %ebx,%ebx
    11e9:	0f 1f 80 00 00 00 00 	nopl   0x0(%rax)
    11f0:	4c 89 f2             	mov    %r14,%rdx
    11f3:	4c 89 ee             	mov    %r13,%rsi
    11f6:	44 89 e7             	mov    %r12d,%edi
    11f9:	41 ff 14 df          	callq  *(%r15,%rbx,8)
    11fd:	48 83 c3 01          	add    $0x1,%rbx
    1201:	48 39 dd             	cmp    %rbx,%rbp
    1204:	75 ea                	jne    11f0 <__libc_csu_init+0x40>
    1206:	48 83 c4 08          	add    $0x8,%rsp
    120a:	5b                   	pop    %rbx
    120b:	5d                   	pop    %rbp
    120c:	41 5c                	pop    %r12
    120e:	41 5d                	pop    %r13
    1210:	41 5e                	pop    %r14
    1212:	41 5f                	pop    %r15
    1214:	c3                   	retq   
    1215:	66 66 2e 0f 1f 84 00 	data16 nopw %cs:0x0(%rax,%rax,1)
    121c:	00 00 00 00 

0000000000001220 <__libc_csu_fini>:
    1220:	f3 0f 1e fa          	endbr64 
    1224:	c3                   	retq   

Disassembly of section .fini:

0000000000001228 <_fini>:
    1228:	f3 0f 1e fa          	endbr64 
    122c:	48 83 ec 08          	sub    $0x8,%rsp
    1230:	48 83 c4 08          	add    $0x8,%rsp
    1234:	c3                   	retq   
