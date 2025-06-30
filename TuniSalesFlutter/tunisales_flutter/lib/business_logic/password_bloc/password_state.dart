part of 'password_bloc.dart';

@immutable
abstract class PasswordState extends Equatable {
  @override
  List<Object> get props => [];
}

class PasswordInitial extends PasswordState {}

class PasswordUpdateSuccess extends PasswordState {
  final String successMsg;

  PasswordUpdateSuccess({required this.successMsg});

  @override
  List<Object> get props => [successMsg];
}

class PasswordUpdateFailure extends PasswordState {
  final String error;

  PasswordUpdateFailure({required this.error});

  @override
  List<Object> get props => [error];
}
