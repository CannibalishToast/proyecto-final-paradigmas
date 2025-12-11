/* ============================================================
   Base dinámica cargada desde MySQL
   ============================================================ */

:- dynamic(enfermedad/2).          % enfermedad(Id, Nombre)
:- dynamic(sintoma/2).             % sintoma(Id, Nombre)
:- dynamic(enfermedad_sintoma/2).  % enfermedad_sintoma(IdEnf, IdSint)


/* ============================================================
   Auxiliares recursivos
   ============================================================ */

% pertenece(X, Lista)
pertenece(X, [X|_]).
pertenece(X, [_|R]) :-
    pertenece(X, R).

% contar_coincidencias(ListaUsuario, ListaEnfermedad, Conteo)
contar_coincidencias(_, [], 0).
contar_coincidencias(SU, [S|R], C) :-
    pertenece(S, SU),
    contar_coincidencias(SU, R, C1),
    C is C1 + 1.

contar_coincidencias(SU, [_|R], C) :-
    contar_coincidencias(SU, R, C).


/* ============================================================
   Obtener lista de síntomas de una enfermedad (sin retract)
   ============================================================ */

sintomas_de_enfermedad(Enf, Lista) :-
    findall(S, enfermedad_sintoma(Enf, S), Lista).


/* ============================================================
   Porcentaje
   ============================================================ */

porcentaje(C, T, P) :-
    T > 0,
    P is (C * 100) / T.


/* ============================================================
   Lista de enfermedades realmente existentes
   evita el bucle infinito
   ============================================================ */

es_enfermedad(Id) :- enfermedad(Id, _).


/* ============================================================
   Diagnóstico principal — Opción A (sin loops)
   diagnosticar(SintomasUsuario, IdEnfermedad, Porcentaje)
   ============================================================ */

diagnosticar(SU, Enf, P) :-
    es_enfermedad(Enf),                  % enfermedad válida
    sintomas_de_enfermedad(Enf, LE),     % síntomas de esa enfermedad
    contar_coincidencias(SU, LE, C),
    length(LE, T),
    porcentaje(C, T, P).


/* ============================================================
   Recomendación
   ============================================================ */

recomendacion(P, "Probabilidad baja, monitorear síntomas.") :-
    P < 40.

recomendacion(P, "Probabilidad moderada, considerar consultar médico.") :-
    P >= 40, P < 70.

recomendacion(P, "Alta probabilidad, se recomienda atención médica.") :-
    P >= 70.

