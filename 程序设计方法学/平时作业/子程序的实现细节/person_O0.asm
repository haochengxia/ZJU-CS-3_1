
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
    1020:	ff 35 9a 2f 00 00    	pushq  0x2f9a(%rip)        # 3fc0 <_GLOBAL_OFFSET_TABLE_+0x8>
    1026:	f2 ff 25 9b 2f 00 00 	bnd jmpq *0x2f9b(%rip)        # 3fc8 <_GLOBAL_OFFSET_TABLE_+0x10>
    102d:	0f 1f 00             	nopl   (%rax)
    1030:	f3 0f 1e fa          	endbr64 
    1034:	68 00 00 00 00       	pushq  $0x0
    1039:	f2 e9 e1 ff ff ff    	bnd jmpq 1020 <.plt>
    103f:	90                   	nop

Disassembly of section .plt.got:

0000000000001040 <__cxa_finalize@plt>:
    1040:	f3 0f 1e fa          	endbr64 
    1044:	f2 ff 25 ad 2f 00 00 	bnd jmpq *0x2fad(%rip)        # 3ff8 <__cxa_finalize@GLIBC_2.2.5>
    104b:	0f 1f 44 00 00       	nopl   0x0(%rax,%rax,1)

Disassembly of section .plt.sec:

0000000000001050 <__stack_chk_fail@plt>:
    1050:	f3 0f 1e fa          	endbr64 
    1054:	f2 ff 25 75 2f 00 00 	bnd jmpq *0x2f75(%rip)        # 3fd0 <__stack_chk_fail@GLIBC_2.4>
    105b:	0f 1f 44 00 00       	nopl   0x0(%rax,%rax,1)

Disassembly of section .text:

0000000000001060 <_start>:
    1060:	f3 0f 1e fa          	endbr64 
    1064:	31 ed                	xor    %ebp,%ebp
    1066:	49 89 d1             	mov    %rdx,%r9
    1069:	5e                   	pop    %rsi
    106a:	48 89 e2             	mov    %rsp,%rdx
    106d:	48 83 e4 f0          	and    $0xfffffffffffffff0,%rsp
    1071:	50                   	push   %rax
    1072:	54                   	push   %rsp
    1073:	4c 8d 05 e6 02 00 00 	lea    0x2e6(%rip),%r8        # 1360 <__libc_csu_fini>
    107a:	48 8d 0d 6f 02 00 00 	lea    0x26f(%rip),%rcx        # 12f0 <__libc_csu_init>
    1081:	48 8d 3d c1 00 00 00 	lea    0xc1(%rip),%rdi        # 1149 <main>
    1088:	ff 15 52 2f 00 00    	callq  *0x2f52(%rip)        # 3fe0 <__libc_start_main@GLIBC_2.2.5>
    108e:	f4                   	hlt    
    108f:	90                   	nop

0000000000001090 <deregister_tm_clones>:
    1090:	48 8d 3d 79 2f 00 00 	lea    0x2f79(%rip),%rdi        # 4010 <__TMC_END__>
    1097:	48 8d 05 72 2f 00 00 	lea    0x2f72(%rip),%rax        # 4010 <__TMC_END__>
    109e:	48 39 f8             	cmp    %rdi,%rax
    10a1:	74 15                	je     10b8 <deregister_tm_clones+0x28>
    10a3:	48 8b 05 2e 2f 00 00 	mov    0x2f2e(%rip),%rax        # 3fd8 <_ITM_deregisterTMCloneTable>
    10aa:	48 85 c0             	test   %rax,%rax
    10ad:	74 09                	je     10b8 <deregister_tm_clones+0x28>
    10af:	ff e0                	jmpq   *%rax
    10b1:	0f 1f 80 00 00 00 00 	nopl   0x0(%rax)
    10b8:	c3                   	retq   
    10b9:	0f 1f 80 00 00 00 00 	nopl   0x0(%rax)

00000000000010c0 <register_tm_clones>:
    10c0:	48 8d 3d 49 2f 00 00 	lea    0x2f49(%rip),%rdi        # 4010 <__TMC_END__>
    10c7:	48 8d 35 42 2f 00 00 	lea    0x2f42(%rip),%rsi        # 4010 <__TMC_END__>
    10ce:	48 29 fe             	sub    %rdi,%rsi
    10d1:	48 89 f0             	mov    %rsi,%rax
    10d4:	48 c1 ee 3f          	shr    $0x3f,%rsi
    10d8:	48 c1 f8 03          	sar    $0x3,%rax
    10dc:	48 01 c6             	add    %rax,%rsi
    10df:	48 d1 fe             	sar    %rsi
    10e2:	74 14                	je     10f8 <register_tm_clones+0x38>
    10e4:	48 8b 05 05 2f 00 00 	mov    0x2f05(%rip),%rax        # 3ff0 <_ITM_registerTMCloneTable>
    10eb:	48 85 c0             	test   %rax,%rax
    10ee:	74 08                	je     10f8 <register_tm_clones+0x38>
    10f0:	ff e0                	jmpq   *%rax
    10f2:	66 0f 1f 44 00 00    	nopw   0x0(%rax,%rax,1)
    10f8:	c3                   	retq   
    10f9:	0f 1f 80 00 00 00 00 	nopl   0x0(%rax)

0000000000001100 <__do_global_dtors_aux>:
    1100:	f3 0f 1e fa          	endbr64 
    1104:	80 3d 05 2f 00 00 00 	cmpb   $0x0,0x2f05(%rip)        # 4010 <__TMC_END__>
    110b:	75 2b                	jne    1138 <__do_global_dtors_aux+0x38>
    110d:	55                   	push   %rbp
    110e:	48 83 3d e2 2e 00 00 	cmpq   $0x0,0x2ee2(%rip)        # 3ff8 <__cxa_finalize@GLIBC_2.2.5>
    1115:	00 
    1116:	48 89 e5             	mov    %rsp,%rbp
    1119:	74 0c                	je     1127 <__do_global_dtors_aux+0x27>
    111b:	48 8b 3d e6 2e 00 00 	mov    0x2ee6(%rip),%rdi        # 4008 <__dso_handle>
    1122:	e8 19 ff ff ff       	callq  1040 <__cxa_finalize@plt>
    1127:	e8 64 ff ff ff       	callq  1090 <deregister_tm_clones>
    112c:	c6 05 dd 2e 00 00 01 	movb   $0x1,0x2edd(%rip)        # 4010 <__TMC_END__>
    1133:	5d                   	pop    %rbp
    1134:	c3                   	retq   
    1135:	0f 1f 00             	nopl   (%rax)
    1138:	c3                   	retq   
    1139:	0f 1f 80 00 00 00 00 	nopl   0x0(%rax)

0000000000001140 <frame_dummy>:
    1140:	f3 0f 1e fa          	endbr64 
    1144:	e9 77 ff ff ff       	jmpq   10c0 <register_tm_clones>

0000000000001149 <main>:
    1149:	f3 0f 1e fa          	endbr64 
    114d:	55                   	push   %rbp
    114e:	48 89 e5             	mov    %rsp,%rbp
    1151:	48 83 ec 60          	sub    $0x60,%rsp
    1155:	64 48 8b 04 25 28 00 	mov    %fs:0x28,%rax
    115c:	00 00 
    115e:	48 89 45 f8          	mov    %rax,-0x8(%rbp)
    1162:	31 c0                	xor    %eax,%eax
    1164:	c7 45 a8 00 00 00 00 	movl   $0x0,-0x58(%rbp)
    116b:	c7 45 ac 01 00 00 00 	movl   $0x1,-0x54(%rbp)
    1172:	48 8d 45 b0          	lea    -0x50(%rbp),%rax
    1176:	8b 55 a8             	mov    -0x58(%rbp),%edx
    1179:	89 d6                	mov    %edx,%esi
    117b:	48 89 c7             	mov    %rax,%rdi
    117e:	e8 2c 00 00 00       	callq  11af <create>
    1183:	48 8d 45 d0          	lea    -0x30(%rbp),%rax
    1187:	8b 55 ac             	mov    -0x54(%rbp),%edx
    118a:	89 d6                	mov    %edx,%esi
    118c:	48 89 c7             	mov    %rax,%rdi
    118f:	e8 1b 00 00 00       	callq  11af <create>
    1194:	b8 00 00 00 00       	mov    $0x0,%eax
    1199:	48 8b 4d f8          	mov    -0x8(%rbp),%rcx
    119d:	64 48 33 0c 25 28 00 	xor    %fs:0x28,%rcx
    11a4:	00 00 
    11a6:	74 05                	je     11ad <main+0x64>
    11a8:	e8 a3 fe ff ff       	callq  1050 <__stack_chk_fail@plt>
    11ad:	c9                   	leaveq 
    11ae:	c3                   	retq   

00000000000011af <create>:
    11af:	f3 0f 1e fa          	endbr64 
    11b3:	55                   	push   %rbp
    11b4:	48 89 e5             	mov    %rsp,%rbp
    11b7:	53                   	push   %rbx
    11b8:	48 81 ec 88 00 00 00 	sub    $0x88,%rsp
    11bf:	48 89 bd 78 ff ff ff 	mov    %rdi,-0x88(%rbp)
    11c6:	89 b5 74 ff ff ff    	mov    %esi,-0x8c(%rbp)
    11cc:	64 48 8b 04 25 28 00 	mov    %fs:0x28,%rax
    11d3:	00 00 
    11d5:	48 89 45 e8          	mov    %rax,-0x18(%rbp)
    11d9:	31 c0                	xor    %eax,%eax
    11db:	83 bd 74 ff ff ff 00 	cmpl   $0x0,-0x8c(%rbp)
    11e2:	75 5c                	jne    1240 <create+0x91>
    11e4:	c7 45 94 00 00 00 00 	movl   $0x0,-0x6c(%rbp)
    11eb:	c7 45 98 00 00 00 00 	movl   $0x0,-0x68(%rbp)
    11f2:	c7 45 9c 00 00 00 00 	movl   $0x0,-0x64(%rbp)
    11f9:	c7 45 c0 14 00 00 00 	movl   $0x14,-0x40(%rbp)
    1200:	c7 45 d0 00 00 00 00 	movl   $0x0,-0x30(%rbp)
    1207:	c7 45 cc 01 00 00 00 	movl   $0x1,-0x34(%rbp)
    120e:	c6 45 c4 54          	movb   $0x54,-0x3c(%rbp)
    1212:	c7 45 d4 b4 00 00 00 	movl   $0xb4,-0x2c(%rbp)
    1219:	c7 45 d8 78 00 00 00 	movl   $0x78,-0x28(%rbp)
    1220:	48 8b 45 c0          	mov    -0x40(%rbp),%rax
    1224:	48 8b 55 c8          	mov    -0x38(%rbp),%rdx
    1228:	48 89 45 a0          	mov    %rax,-0x60(%rbp)
    122c:	48 89 55 a8          	mov    %rdx,-0x58(%rbp)
    1230:	48 8b 45 d0          	mov    -0x30(%rbp),%rax
    1234:	48 89 45 b0          	mov    %rax,-0x50(%rbp)
    1238:	8b 45 d8             	mov    -0x28(%rbp),%eax
    123b:	89 45 b8             	mov    %eax,-0x48(%rbp)
    123e:	eb 63                	jmp    12a3 <create+0xf4>
    1240:	83 bd 74 ff ff ff 01 	cmpl   $0x1,-0x8c(%rbp)
    1247:	75 5a                	jne    12a3 <create+0xf4>
    1249:	c7 45 88 01 00 00 00 	movl   $0x1,-0x78(%rbp)
    1250:	c7 45 8c 00 00 00 00 	movl   $0x0,-0x74(%rbp)
    1257:	c7 45 90 01 00 00 00 	movl   $0x1,-0x70(%rbp)
    125e:	c7 45 c0 14 00 00 00 	movl   $0x14,-0x40(%rbp)
    1265:	c7 45 d0 01 00 00 00 	movl   $0x1,-0x30(%rbp)
    126c:	c7 45 cc 00 00 00 00 	movl   $0x0,-0x34(%rbp)
    1273:	c6 45 c4 4c          	movb   $0x4c,-0x3c(%rbp)
    1277:	c7 45 d4 aa 00 00 00 	movl   $0xaa,-0x2c(%rbp)
    127e:	c7 45 d8 64 00 00 00 	movl   $0x64,-0x28(%rbp)
    1285:	48 8b 45 c0          	mov    -0x40(%rbp),%rax
    1289:	48 8b 55 c8          	mov    -0x38(%rbp),%rdx
    128d:	48 89 45 a0          	mov    %rax,-0x60(%rbp)
    1291:	48 89 55 a8          	mov    %rdx,-0x58(%rbp)
    1295:	48 8b 45 d0          	mov    -0x30(%rbp),%rax
    1299:	48 89 45 b0          	mov    %rax,-0x50(%rbp)
    129d:	8b 45 d8             	mov    -0x28(%rbp),%eax
    12a0:	89 45 b8             	mov    %eax,-0x48(%rbp)
    12a3:	48 8b 85 78 ff ff ff 	mov    -0x88(%rbp),%rax
    12aa:	48 8b 4d a0          	mov    -0x60(%rbp),%rcx
    12ae:	48 8b 5d a8          	mov    -0x58(%rbp),%rbx
    12b2:	48 89 08             	mov    %rcx,(%rax)
    12b5:	48 89 58 08          	mov    %rbx,0x8(%rax)
    12b9:	48 8b 55 b0          	mov    -0x50(%rbp),%rdx
    12bd:	48 89 50 10          	mov    %rdx,0x10(%rax)
    12c1:	8b 55 b8             	mov    -0x48(%rbp),%edx
    12c4:	89 50 18             	mov    %edx,0x18(%rax)
    12c7:	48 8b 45 e8          	mov    -0x18(%rbp),%rax
    12cb:	64 48 33 04 25 28 00 	xor    %fs:0x28,%rax
    12d2:	00 00 
    12d4:	74 05                	je     12db <create+0x12c>
    12d6:	e8 75 fd ff ff       	callq  1050 <__stack_chk_fail@plt>
    12db:	48 8b 85 78 ff ff ff 	mov    -0x88(%rbp),%rax
    12e2:	48 81 c4 88 00 00 00 	add    $0x88,%rsp
    12e9:	5b                   	pop    %rbx
    12ea:	5d                   	pop    %rbp
    12eb:	c3                   	retq   
    12ec:	0f 1f 40 00          	nopl   0x0(%rax)

00000000000012f0 <__libc_csu_init>:
    12f0:	f3 0f 1e fa          	endbr64 
    12f4:	41 57                	push   %r15
    12f6:	4c 8d 3d bb 2a 00 00 	lea    0x2abb(%rip),%r15        # 3db8 <__frame_dummy_init_array_entry>
    12fd:	41 56                	push   %r14
    12ff:	49 89 d6             	mov    %rdx,%r14
    1302:	41 55                	push   %r13
    1304:	49 89 f5             	mov    %rsi,%r13
    1307:	41 54                	push   %r12
    1309:	41 89 fc             	mov    %edi,%r12d
    130c:	55                   	push   %rbp
    130d:	48 8d 2d ac 2a 00 00 	lea    0x2aac(%rip),%rbp        # 3dc0 <__init_array_end>
    1314:	53                   	push   %rbx
    1315:	4c 29 fd             	sub    %r15,%rbp
    1318:	48 83 ec 08          	sub    $0x8,%rsp
    131c:	e8 df fc ff ff       	callq  1000 <_init>
    1321:	48 c1 fd 03          	sar    $0x3,%rbp
    1325:	74 1f                	je     1346 <__libc_csu_init+0x56>
    1327:	31 db                	xor    %ebx,%ebx
    1329:	0f 1f 80 00 00 00 00 	nopl   0x0(%rax)
    1330:	4c 89 f2             	mov    %r14,%rdx
    1333:	4c 89 ee             	mov    %r13,%rsi
    1336:	44 89 e7             	mov    %r12d,%edi
    1339:	41 ff 14 df          	callq  *(%r15,%rbx,8)
    133d:	48 83 c3 01          	add    $0x1,%rbx
    1341:	48 39 dd             	cmp    %rbx,%rbp
    1344:	75 ea                	jne    1330 <__libc_csu_init+0x40>
    1346:	48 83 c4 08          	add    $0x8,%rsp
    134a:	5b                   	pop    %rbx
    134b:	5d                   	pop    %rbp
    134c:	41 5c                	pop    %r12
    134e:	41 5d                	pop    %r13
    1350:	41 5e                	pop    %r14
    1352:	41 5f                	pop    %r15
    1354:	c3                   	retq   
    1355:	66 66 2e 0f 1f 84 00 	data16 nopw %cs:0x0(%rax,%rax,1)
    135c:	00 00 00 00 

0000000000001360 <__libc_csu_fini>:
    1360:	f3 0f 1e fa          	endbr64 
    1364:	c3                   	retq   

Disassembly of section .fini:

0000000000001368 <_fini>:
    1368:	f3 0f 1e fa          	endbr64 
    136c:	48 83 ec 08          	sub    $0x8,%rsp
    1370:	48 83 c4 08          	add    $0x8,%rsp
    1374:	c3                   	retq   
