package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.game.FieldZone;

public final class SIGNI_W3_CodeMazeDekasanbashi extends Card {
    
    public SIGNI_W3_CodeMazeDekasanbashi()
    {
        setImageSets("WXDi-P02-053");
        
        setOriginalName("コードメイズ　デカサンバシ");
        setAltNames("コードメイズデカサンバシ Koodo Meizu Dekasanbashi");
        setDescription("jp",
                "@U：このシグニの正面のシグニがアタックしたとき、このシグニを他のシグニゾーンに配置してもよい。\n" +
                "@U：対戦相手のターン終了時、対戦相手のシグニ１体を対象とし、%W %Wを支払ってもよい。そうした場合、それを手札に戻す。"
        );
        
        setName("en", "Dekasanbashi, Code: Maze");
        setDescription("en",
                "@U: Whenever the SIGNI in front of this SIGNI attacks, you may move this SIGNI to another SIGNI Zone.\n" +
                "@U: At the end of your opponent's turn, you may pay %W %W. If you do, return target SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Code Maze Dekasanbashi");
        setDescription("en_fan",
                "@U: Whenever the SIGNI in front of this SIGNI attacks, you may move this SIGNI onto another SIGNI zone.\n" +
                "@U: At the end of your opponent's turn, target 1 of your opponent's SIGNI and you may pay %W %W. If you do, return it to their hand."
        );
        
		setName("zh_simplified", "迷宫代号 江岛大桥");
        setDescription("zh_simplified", 
                "@U :当这只精灵的正面的精灵攻击时，可以把这只精灵往其他的精灵区配置。\n" +
                "@U :对战对手的回合结束时，对战对手的精灵1只作为对象，可以支付%W %W。这样做的场合，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getCaller().getLocation() == CardLocation.getOppositeSIGNILocation(getCardIndex().getLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            FieldZone fieldZone = playerTargetZone(0,1, new TargetFilter(TargetHint.MOVE).own().SIGNI().unoccupied()).get();
            if(fieldZone != null) moveToZone(getCardIndex(), fieldZone.getZoneLocation());
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.WHITE, 2)))
            {
                addToHand(target);
            }
        }
    }
}
