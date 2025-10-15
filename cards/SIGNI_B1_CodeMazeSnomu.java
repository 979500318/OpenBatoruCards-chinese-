package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B1_CodeMazeSnomu extends Card {
    
    public SIGNI_B1_CodeMazeSnomu()
    {
        setImageSets("WXDi-P02-065");
        
        setOriginalName("コードメイズ　スノミュ");
        setAltNames("コードメイズスノミュ Koodo Meizu Sunomyu");
        setDescription("jp",
                "@U：あなたのターン終了時、対戦相手の場に凍結状態のシグニが２体以上ある場合、対戦相手の凍結状態のシグニ１体を対象とし、それをデッキの一番下に置く。\n" +
                "@E：対戦相手の中央のシグニゾーンにあるシグニ１体を対象とし、それを凍結する。"
        );
        
        setName("en", "Snow Myu, Code: Maze");
        setDescription("en",
                "@U: At the end of your turn, if there are two or more frozen SIGNI on your opponent's field, put target frozen SIGNI on your opponent's field on the bottom of its owner's deck.\n" +
                "@E: Freeze target SIGNI in your opponent's center SIGNI Zone."
        );
        
        setName("en_fan", "Code Maze Snomu");
        setDescription("en_fan",
                "@U: At the end of your turn, if there are 2 or more frozen SIGNI on your opponent's field, target 1 of your opponent's frozen SIGNI, and put it on the bottom of their deck.\n" +
                "@E: Target 1 SIGNI in your opponent's center SIGNI zone, and freeze it."
        );
        
		setName("zh_simplified", "迷宫代号 雪诺姆");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，对战对手的场上的冻结状态的精灵在2只以上的场合，对战对手的冻结状态的精灵1只作为对象，将其放置到牌组最下面。\n" +
                "@E :对战对手的中央的精灵区的精灵1只作为对象，将其冻结。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(1);
        setPower(2000);
        
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
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().OP().SIGNI().withState(CardStateFlag.FROZEN).getValidTargetsCount() >= 2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().withState(CardStateFlag.FROZEN)).get();
                returnToDeck(target, DeckPosition.BOTTOM);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI().fromLocation(CardLocation.SIGNI_CENTER)).get();
            freeze(target);
        }
    }
}
