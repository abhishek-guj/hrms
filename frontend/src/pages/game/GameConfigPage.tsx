import React from "react";
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
} from "../../components/ui/field";
import { Input } from "../../components/ui/input";

const GameConfigPage = () => {
  return (
    <div>
      <GameAddForm />
    </div>
  );
};

export default GameConfigPage;

const GameAddForm = () => {
  return (
    <div className="flex justify-center p-4">
      <form
        // onSubmit={onSubmit}
        className="flex flex-col p-2 gap-2 border w-1/2"
      >
        <FieldGroup>
          <div>Add Game</div>
        </FieldGroup>
        <Field>
          <FieldInput
            displayName={"Test"}
            name="Test"
            // errors={errors}
            type="text"
            placeholder="For this Business..."
          />
        </Field>
      </form>
    </div>
  );
};

export function FieldInput({
  name,
  displayName,
  errors,
  ...props
}: Readonly<{
  register: UseFormRegister<any>;
  name: string;
  displayName: string;
  errors: { message?: string } | undefined;
}>) {
  return (
    <Field>
      <FieldLabel htmlFor={name}>{displayName}</FieldLabel>
      <Input id={name} {...props} />
      {errors && <FieldError errors={[errors]} />}
    </Field>
  );
}
