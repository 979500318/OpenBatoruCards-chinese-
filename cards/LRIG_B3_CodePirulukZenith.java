package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.ExceedCost;

public final class LRIG_B3_CodePirulukZenith extends Card {

    public LRIG_B3_CodePirulukZenith()
    {
        setImageSets("WXDi-P13-008", "WXDi-P13-008U");

        setOriginalName("コード・ピルルク・極");
        setAltNames("コードピルルクキワメ Koodo Piruruku Kiwame");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが#Sの場合、あなたのトラッシュから#Sのスペル１枚を対象とし、それを使用してもよい。\n" +
                "@E：カードを３枚引き、手札を２枚捨てる。\n" +
                "@A @[エクシード４]@：ターン終了時まで、このルリグは@>@U：あなたが#Sのスペルを使用したとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－4000する。@@を得る。"
        );

        setName("en", "Code Piruluk Ultimate");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all the SIGNI on your field are #S, you may use target #S spell from your trash. \n@E: Draw three cards and discard two cards.\n@A @[Exceed 4]@: This LRIG gains@>@U: Whenever you use a #S spell, target SIGNI on your opponent's field gets --4000 power until end of turn.@@until end of turn."
        );
        
        setName("en_fan", "Code Piruluk Zenith");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if all of your SIGNI are #S @[Dissona]@ SIGNI, you may target 1 #S @[Dissona]@ spell from your trash, and use it.\n" +
                "@E: Draw 3 cards, and discard 2 cards from your hand.\n" +
                "@A @[Exceed 4]@: Until end of turn, this LRIG gains:" +
                "@>@U: Whenever you use a #S @[Dissona]@ spell, target 1 of your opponent's SIGNI, and until end of turn, it gets --4000 power."
        );

		setName("zh_simplified", "代号·皮璐璐可·极");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，你的场上的全部的精灵是#S的场合，从你的废弃区把#S的魔法1张作为对象，可以将其使用。（把费用支付）\n" +
                "@E :抽3张牌，手牌2张舍弃。\n" +
                "@A @[超越 4]@:直到回合结束时为止，这只分身得到\n" +
                "@>@U 当你把#S的魔法使用时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-4000。@@\n"
        );

        setCardFlags(CardFlag.DISSONA);

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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);

            registerActionAbility(new ExceedCost(4), this::onActionEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getSIGNICount(getOwner()) > 0 && new TargetFilter().own().SIGNI().not(new TargetFilter().dissona()).getValidTargetsCount() == 0)
            {
                CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.ACTIVATE).own().spell().fromTrash().playable()).get();
                use(target);
            }
        }
        
        private void onEnterEff()
        {
            draw(3);
            discard(2);
        }
        
        private void onActionEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.USE_SPELL, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().isState(CardStateFlag.IS_DISSONA) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -4000, ChronoDuration.turnEnd());
        }
    }
}

