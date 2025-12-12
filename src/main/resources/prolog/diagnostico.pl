

:- dynamic(enfermedad/2).          % enfermedad(Id, Nombre)
:- dynamic(sintoma/2).             % sintoma(Id, Nombre)
:- dynamic(enfermedad_sintoma/2).  % enfermedad_sintoma(IdEnf, IdSint).

pertenece(X, [X|_]) :- !.
pertenece(X, [_|R]) :-
    pertenece(X, R).

contar_coincidencias(_, [], 0) :- !.
contar_coincidencias(SU, [S|R], C) :-
    pertenece(S, SU), !,
    contar_coincidencias(SU, R, C1),
    C is C1 + 1.
contar_coincidencias(SU, [_|R], C) :-
    contar_coincidencias(SU, R, C).

sintomas_de_enfermedad(Enf, Lista) :-
    findall(S, enfermedad_sintoma(Enf, S), Lista).

porcentaje(C, T, P) :-
    T > 0,
    P is (C * 100) / T.

diagnosticar(SU, Enf, P) :-
    enfermedad(Enf, _),          % enfermedad válida
    sintomas_de_enfermedad(Enf, LE),
    contar_coincidencias(SU, LE, C),
    length(LE, T),
    porcentaje(C, T, P).
