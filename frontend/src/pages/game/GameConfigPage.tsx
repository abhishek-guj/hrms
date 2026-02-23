import React from "react";
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
  FieldSet,
} from "../../components/ui/field";
import { Input } from "../../components/ui/input";
import { useCreateGame } from "../../components/game/queries/game.queries";
import type { GameReqDto } from "../../components/game/types/game.types";
import { useForm, type SubmitHandler } from "react-hook-form";
import { Separator } from "../../components/ui/separator";
import { FieldInput } from "../../components/game/forms/BookGameSlotForm";
import { Button } from "../../components/ui/button";

const GameConfigPage = () => {
  return (
    <div>
      <GameAddForm />
    </div>
  );
};

export default GameConfigPage;

const GameAddForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<GameReqDto>({
    mode: "onBlur",
  });

  const createGame = useCreateGame();

  const onSubmit: SubmitHandler<GameReqDto> = async (data) => {
    const resData = await createGame.mutateAsync({ dto: data });
  };

  return (
    <div className="flex justify-center p-4">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="flex flex-col gap-2 py-2 p-10 border"
      >
        <div className="w-96 flex flex-col gap-4 ">
          <FieldGroup>
            <div className="text-lg font-black">Add Game</div>
          </FieldGroup>
          <Separator />
          <FieldGroup className="flex">
            <FieldSet>
              <FieldInput
                displayName={"Game Name"}
                name="gameTypeName"
                errors={errors.gameTypeName}
                type="text"
                register={register}
                placeholder="Ex. PS5"
              />
            </FieldSet>
          </FieldGroup>
          <Separator />
          <FieldGroup className="flex flex-row w-full gap-4">
            <FieldSet>
              <FieldInput
                displayName={"Slot Duration"}
                name="maxSlotDurationMinutes"
                errors={errors.maxSlotDurationMinutes}
                register={register}
                type="text"
                placeholder="In Minutes: 10,20,30..."
              />
            </FieldSet>
            <FieldSet>
              <FieldInput
                displayName={"Slot Sizes"}
                name="slotSizes"
                errors={errors.slotSizes}
                register={register}
                type="text"
                placeholder="list of commas = 2,4,6"
              />
            </FieldSet>
          </FieldGroup>
          <Separator />
          <FieldGroup className="flex flex-row gap-4">
            <FieldSet>
              <FieldInput
                displayName={"Start Time"}
                name="startTime"
                errors={errors.startTime}
                register={register}
                type="text"
                placeholder="list of commas = 2,4,6"
              />
            </FieldSet>
            <FieldSet>
              <FieldInput
                displayName={"End Time"}
                name="endTime"
                errors={errors.endTime}
                register={register}
                type="text"
                placeholder="list of commas = 2,4,6"
              />
            </FieldSet>
          </FieldGroup>
          <Separator />
          <Field>
            <Button>Create</Button>
          </Field>
        </div>
      </form>
    </div>
  );
};

// export function FieldInput({
//   name,
//   displayName,
//   errors,
//   ...props
// }: Readonly<{
//   register: UseFormRegister<any>;
//   name: string;
//   displayName: string;
//   errors: { message?: string } | undefined;
// }>) {
//   return (
//     <Field>
//       <FieldLabel htmlFor={name}>{displayName}</FieldLabel>
//       <Input id={name} {...props} />
//       {errors && <FieldError errors={[errors]} />}
//     </Field>
//   );
// }
