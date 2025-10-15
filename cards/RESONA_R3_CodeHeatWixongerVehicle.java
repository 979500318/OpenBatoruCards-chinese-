package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class RESONA_R3_CodeHeatWixongerVehicle extends Card {

    public RESONA_R3_CodeHeatWixongerVehicle()
    {
        setImageSets("WX25-P2-TK03");

        setOriginalName("コードヒート　ウイクロソジャービークル");
        setAltNames("コードヒートウイクロソジャービークル Koodo Hiito Uikurosojaabiikuru");
        setDescription("jp",
                "手札とエナゾーンからシグニを合計２枚トラッシュに置く\n\n" +
                "@U：あなたのアタックフェイズ開始時、対戦相手のエナゾーンにカードが２枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n" +
                "@E：あなたのトラッシュからスペル１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Code Heat Wixonger Vehicle");
        setDescription("en",
                "Put 2 SIGNI from your hand and/or ener zone into the trash\n\n" +
                "@U: At the beginning of your attack phase, if there are 2 or more cards in your opponent's ener zone, your opponent chooses 1 card from their ener zone, and puts it into the trash.\n" +
                "@E: Target 1 spell from your trash, and add it to your hand."
        );

		setName("zh_simplified", "赤日代号 愿望交错战车");
        setDescription("zh_simplified", 
                "[[出现条件]]主要阶段从手牌和能量区把精灵合计2张放置到废弃区\n" +
                "@U :你的攻击阶段开始时，对战对手的能量区的牌在2张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "@E :从你的废弃区把魔法1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.RESONA);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(12000);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            setUseCondition(UseCondition.RESONA, 2, new TargetFilter().or(new TargetFilter().fromHand(), new TargetFilter().fromEner()));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getEnerCount(getOpponent()) >= 2)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().spell().fromTrash()).get();
            addToHand(target);
        }
    }
}

