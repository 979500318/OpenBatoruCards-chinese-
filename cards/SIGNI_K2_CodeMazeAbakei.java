package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K2_CodeMazeAbakei extends Card {

    public SIGNI_K2_CodeMazeAbakei()
    {
        setImageSets("WX24-P2-092");

        setOriginalName("コードメイズ　アバケイ");
        setAltNames("コードメイズアバケイ Koodo Meizu Abakei");
        setDescription("jp",
                "@U $T1：あなたのメインフェイズの間、あなたの他の＜迷宮＞のシグニ１体が場に出たとき、対戦相手の場にあるすべてのシグニを好きなように配置し直す。\n" +
                "@E %K %X：あなたのトラッシュから＜迷宮＞のシグニ１枚を対象とし、それを手札に加える。" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Code Maze Abakei");
        setDescription("en",
                "@U $T1: During your main phase, when 1 of your other <<Labyrinth>> SIGNI enters the field, rearrange all of your opponent's SIGNI on the field as you like.\n" +
                "@E %K %X: Target 1 <<Labyrinth>> SIGNI from your trash, and add it to your hand." +
                "~#Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "迷宫代号 博物馆网走监狱");
        setDescription("zh_simplified", 
                "@U $T1 :你的主要阶段期间，当你的其他的<<迷宮>>精灵1只出场时，对战对手的场上的全部的精灵任意重新配置。\n" +
                "@E %K%X:从你的废弃区把<<迷宮>>精灵1张作为对象，将其加入手牌。" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
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
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto1.setCondition(this::onAutoEffCond);
            auto1.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN &&
                   isOwnCard(caller) && caller != getCardIndex() && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.LABYRINTH) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            rearrangeAll(getOpponent());
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.LABYRINTH).fromTrash()).get();
            addToHand(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}
