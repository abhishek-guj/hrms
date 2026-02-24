import { useForm, type SubmitHandler } from "react-hook-form";
import {
  GameCreateSchema,
  type GameCreateSchemaType,
} from "../../login/schema";
import { zodResolver } from "@hookform/resolvers/zod";
import { useCreateGame, useUpdateGame } from "../queries/game.queries";
import { Field, FieldGroup, FieldSet } from "../../ui/field";
import { Separator } from "../../ui/separator";
import { FieldInput } from "./BookGameSlotForm";
import { Button } from "../../ui/button";
import type { GameDetailsDto, GameReqDto } from "../types/game.types";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

const GameConfigForm = ({
  gameData,
  isEdit = false,
}: {
  gameData?: GameReqDto;
  isEdit: boolean;
}) => {
  //
  //
  //
  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
    reset,
  } = useForm({
    mode: "onBlur",
    resolver: zodResolver(GameCreateSchema),
    // defaultValues: gameData,
  });

  useEffect(() => {
    if (isEdit) {
      let slots = gameData?.slotSizes.join(",");
      reset({ ...gameData, slotSizes: slots });
    }
  }, [gameData]);

  const createGame = useCreateGame();
  const updateGame = useUpdateGame();

  // handlers
  const handleCreate = async (data: GameCreateSchemaType) => {
    const slots = isEdit ? data?.slotSizes : data?.slotSizes.split(",");
    data = { ...data, slotSizes: slots };
    const resData = await createGame.mutateAsync({ dto: data });
  };
  const handleUpdate = async (data: GameCreateSchemaType) => {
    const slots = Array.isArray(data?.slotSizes)
      ? data.slotSizes
      : data.slotSizes.split(",");
    data = { ...data, slotSizes: slots };

    const resData = await updateGame.mutateAsync({
      gameId: data.gameTypeId!,
      dto: data,
    });
  };

  const onSubmit: SubmitHandler<GameCreateSchemaType> = async (data) => {
    if (isEdit) {
      await handleUpdate(data);
    } else {
      await handleCreate(data);
    }
  };

  // derived
  const buttonName = isEdit ? "Update" : "Create";
  return (
    // <div className="flex justify-center p-4">
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="flex flex-col gap-2 py-2 p-10"
    >
      <div className="w-96 flex flex-col gap-4 ">
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
              type="number"
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
              type="time"
              placeholder="start time = 16:00"
            />
          </FieldSet>
          <FieldSet>
            <FieldInput
              displayName={"End Time"}
              name="endTime"
              errors={errors.endTime}
              register={register}
              type="time"
              placeholder="end time = 16:00"
            />
          </FieldSet>
        </FieldGroup>
        <Separator />
        <Field>
          <Button>{buttonName}</Button>
        </Field>
      </div>
    </form>
    // </div>
  );
};

export default GameConfigForm;
