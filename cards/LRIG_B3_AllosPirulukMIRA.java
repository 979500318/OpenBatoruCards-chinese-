package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
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
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_B3_AllosPirulukMIRA extends Card {

    public LRIG_B3_AllosPirulukMIRA()
    {
        setImageSets("WXDi-P11-006", "WXDi-P11-006U");

        setOriginalName("アロス・ピルルク　ＭＩＲＡ");
        setAltNames("アロスピルルクミラ Arosu Piruruku Mira");
        setDescription("jp",
                "@U：あなたがシグニを１枚捨てたとき、あなたのチェックゾーンにあるカードが４枚以下の場合、そのシグニをトラッシュからチェックゾーンに置いてもよい。\n" +
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをあなたのチェックゾーンにあるカード１枚につき－1000する。その後、あなたのチェックゾーンから#Gを持たないカードを１枚まで対象とし、それを手札に加える。\n" +
                "@A $G1 %B0：カードを３枚引き、手札を２枚捨てる。"
        );

        setName("en", "Allos Piruluk Mira");
        setDescription("en",
                "@U: Whenever you discard a SIGNI, if you have four or fewer cards in your Check Zone, you may put that SIGNI from your trash into your Check Zone.\n" +
                "@U: At the beginning of your attack phase, target SIGNI on your opponent's field gets --1000 power for each card in your Check Zone until end of turn. Then, add up to one target card without a #G from your Check Zone to your hand.\n" +
                "@A $G1 %B0: Draw three cards and discard two cards."
        );
        
        setName("en_fan", "Allos Piruluk MIRA");
        setDescription("en_fan",
                "@U: Whenever you discard a SIGNI, if there are 4 or less cards in your check zone, you may put that SIGNI from your trash into the check zone.\n" +
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power for each card in your check zone. Then, target up to 1 card without #G @[Guard]@ from your check zone, and add it to your hand.\n" +
                "@A $G1 %B0: Draw 3 cards, and discard 2 cards from your hand."
        );

		setName("zh_simplified", "阿洛斯·皮璐璐可 MIRA");
        setDescription("zh_simplified", 
                "@U :当你把精灵1张舍弃时，你的检查区的牌在4张以下的场合，可以把那张精灵从废弃区放置到检查区。\n" +
                "@U 你的攻击阶段开始时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据你的检查区的牌的数量，每有1张就-1000。然后，从你的检查区把不持有#G的牌1张最多作为对象，将其加入手牌。\n" +
                "@A $G1 %B0:抽3张牌，手牌2张舍弃。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getCheckZoneCount(getOwner()) <= 4 && caller.getLocation() == CardLocation.TRASH && playerChoiceActivate())
            {
                putInCheckZone(caller);
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -1000 * getCheckZoneCount(getOwner()), ChronoDuration.turnEnd());
            
            target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromCheckZone()).get();
            addToHand(target);
        }
        
        private void onActionEff()
        {
            draw(3);
            discard(2);
        }
    }
}
