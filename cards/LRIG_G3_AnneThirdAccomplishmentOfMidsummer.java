package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class LRIG_G3_AnneThirdAccomplishmentOfMidsummer extends Card {

    public LRIG_G3_AnneThirdAccomplishmentOfMidsummer()
    {
        setImageSets("WXDi-P14-008", "WXDi-P14-008U");
        setLinkedImageSets("WXDi-P14-TK01","WXDi-P14-TK02","WXDi-P14-TK03","WXDi-P14-TK04","WXDi-P14-TK05");

        setOriginalName("盛夏の成果　アン＝サード");
        setAltNames("セイカノセイカアンサード Seikaisei An Saado");
        setDescription("jp",
                "@U：あなたのメインフェイズ開始時、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。\n" +
                "@A $T1 %G0：あなたのシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは@>@C：対戦相手のターンの間、[[シャドウ（レベル３以上）]]を得る。@@を得る。\n" +
                "@A @[エクシード４]@：フェゾーネマジックのクラフトから２種類を１枚ずつ公開しルリグデッキに加える。"
        );

        setName("en", "Ann III, Summer Success");
        setDescription("en",
                "@U: At the beginning of your main phase, add up to one target SIGNI from your Ener Zone to your hand.\n@A $T1 %G0: Target SIGNI on your field gains@>@C: During your opponent's turn, this SIGNI gains [[Shadow -- Level three or more]].@@until the end of your opponent's next end phase.\n@A @[Exceed 4]@: Reveal two different Fesonne Magic Craft and add them to your LRIG Deck. "
        );
        
        setName("en_fan", "Anne-Third, Accomplishment of Midsummer");
        setDescription("en_fan",
                "@U: At the beginning of your main phase, target up to 1 SIGNI from your ener zone, and add it to your hand.\n" +
                "@A $T1 %G0: Target 1 of your SIGNI, and until the end of your opponent's next turn, it gains:" +
                "@>@C: During your opponent's turn, this SIGNI gains [[Shadow (level 3 or higher)]].@@" +
                "@A @[Exceed 4]@: Reveal 2 different Fessone Magic crafts one by one, and add them to your LRIG deck."
        );

		setName("zh_simplified", "盛夏的成果 安=THIRD");
        setDescription("zh_simplified", 
                "@U :你的主要阶段开始时，从你的能量区把精灵1张最多作为对象，将其加入手牌。\n" +
                "@A $T1 %G0:你的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到\n" +
                "@>@C :对战对手的回合期间，得到[[暗影（等级3以上）]]。@@\n" +
                "@A @[超越 4]@:从音乐节魔术的衍生把2种类各1张公开加入分身牌组。（音乐节魔术有5种类）\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            registerActionAbility(new ExceedCost(4), this::onActionEff2);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            
            if(target != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
                attachedConst.setCondition(this::onAttachedConstEffCond);
                
                attachAbility(target, attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getLevel().getValue() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onActionEff2()
        {
            playerChoiceFessoneMagic();
        }
    }
}
