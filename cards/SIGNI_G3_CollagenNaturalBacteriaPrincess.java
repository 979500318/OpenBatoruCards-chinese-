package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G3_CollagenNaturalBacteriaPrincess extends Card {

    public SIGNI_G3_CollagenNaturalBacteriaPrincess()
    {
        setImageSets("WX25-P1-060");

        setOriginalName("羅菌姫　コラーゲン");
        setAltNames("ラキンヒメコラーゲン Rakinhime Koraagen");
        setDescription("jp",
                "@U：各ターン終了時、【エナチャージ１】をする。\n\n" +
                "@U：このカードが対戦相手の効果によっていずれかの領域からトラッシュに置かれたとき、あなたのエナゾーンにあるカードが２枚以下の場合、【エナチャージ１】をする。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Collagen, Natural Bacteria Princess");
        setDescription("en",
                "@U: At the end of each turn, [[Ener Charge 1]].\n\n" +
                "@U: When this card is put from any zone into the trash by your opponent's effect, if there are 2 or less cards in your ener zone, [[Ener Charge 1]]." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "罗菌姬 胶原蛋白");
        setDescription("zh_simplified", 
                "@U :各回合结束时，[[能量填充1]]。\n" +
                "@U :当这张牌因为对战对手的效果从任一个领域放置到废弃区时，你的能量区的牌在2张以下的场合，[[能量填充1]]。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.TRASH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.getFlags().addValue(AbilityFlag.IGNORE_LOCATION | AbilityFlag.IGNORE_UNDER_FLAGS);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            enerCharge(1);
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSource()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2()
        {
            if(getEnerCount(getOwner()) <= 2)
            {
                enerCharge(1);
            }
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
