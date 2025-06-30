part of 'password_bloc.dart';

@immutable
abstract class PasswordEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class UpdatePassword extends PasswordEvent {

  final String currentPassword;
  final String newPassword;

  UpdatePassword(this.currentPassword,this.newPassword);

  @override
  List<Object> get props => [];
}

