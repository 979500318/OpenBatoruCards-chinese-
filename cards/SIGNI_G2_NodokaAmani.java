package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_G2_NodokaAmani extends Card {

    public SIGNI_G2_NodokaAmani()
    {
        setImageSets("WX25-CP1-078");

        setOriginalName("天見ノドカ");
        setAltNames("アマミノドカ Amani Nodoka");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの他の緑の＜ブルアカ＞のシグニ１体を対象とし、ターン終了時まで、それのパワーを＋3000し、それは[[シャドウ（レベル３以上のシグニ）]]を得る。" +
                "~{{C：【シャドウ（パワー8000以下のシグニ）】@@" +
                "~#：【エナチャージ２】をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。"
        );

        setName("en", "Amani Nodoka");

        setName("en_fan", "Nodoka Amani");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your other green <<Blue Archive>> SIGNI, and until end of turn, it gets +3000 power and [[Shadow (level 3 or higher SIGNI)]]." +
                "~{{C: [[Shadow (SIGNI with power 8000 or less)]]@@" +
                "~#[[Ener Charge 2]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand."
        );

		setName("zh_simplified", "天见和香");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的其他的绿色的<<ブルアカ>>精灵1只作为对象，直到回合结束时为止，其的力量+3000，其得到[[暗影（等级3以上的精灵）]]。\n" +
                "~{{C:[[暗影（力量8000以下的精灵）]]@@" +
                "~#[[能量填充2]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ConstantAbility cont = registerConstantAbility(new AbilityGainModifier(this::onConstEffModGetSample));
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.GREEN).withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex())).get();
            
            if(target != null)
            {
                gainPower(target, 3000, ChronoDuration.turnEnd());
                
                attachAbility(target, new StockAbilityShadow(this::onAttachedStockEff1AddCond), ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedStockEff1AddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                    cardIndexSource.getIndexedInstance().getLevel().getValue() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEff2AddCond));
        }
        private ConditionState onAttachedStockEff2AddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                    cardIndexSource.getIndexedInstance().getPower().getValue() <= 8000 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onLifeBurstEff()
        {
            enerCharge(2);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
    }
}
