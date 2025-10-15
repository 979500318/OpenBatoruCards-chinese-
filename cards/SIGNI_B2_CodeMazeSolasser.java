package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_B2_CodeMazeSolasser extends Card {
    
    public SIGNI_B2_CodeMazeSolasser()
    {
        setImageSets("WXDi-P00-063");
        
        setOriginalName("コードメイズ　ソラサー");
        setAltNames("コードメイズソラサー Koodo Meizu Sorasaa");
        setDescription("jp",
                "@E：あなたの他のシグニ１体を対象とし、それとこのシグニの場所を入れ替える。\n" +
                "@U $T1：あなたの効果によって場にあるこのシグニが他のシグニゾーンに移動したとき、対戦相手はデッキの一番上を公開する。あなたはそれを対戦相手のデッキの一番下に置いてもよい。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );
        
        setName("en", "High Circus, Code:Maze");
        setDescription("en",
                "@E: Swap positions of this SIGNI with another target SIGNI on your field.\n" +
                "@U $T1: When this SIGNI moves to a different SIGNI Zone by your effect, your opponent reveals the top card of their deck. You may put that card on the bottom of your opponent's deck." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Code Maze Solasser");
        setDescription("en_fan",
                "@E: Target 1 of your other SIGNI, and exchange its position with this SIGNI.\n" +
                "@U $T1: When this SIGNI on the field is moved to another SIGNI zone by your effect, your opponent reveals the top card of their deck. You may put it on the bottom of your opponent's deck." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );
        
		setName("zh_simplified", "迷宫代号 空中探照灯");
        setDescription("zh_simplified", 
                "@E :你的其他的精灵1只作为对象，将其与这只精灵的场所交换。\n" +
                "@U $T1 :当因为你的效果把场上的这只精灵往其他的精灵区移动时，对战对手的牌组最上面公开。你可以将其放置到对战对手的牌组最下面。" +
                "~#对战对手的精灵1只作为对象，将其#D并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MOVE).own().SIGNI().except(getCardIndex())).get();
            exchange(getCardIndex(), target);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSourceCardIndex()) && getEvent().getSourceCost() == null &&
                   CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex cardIndex = reveal(getOpponent());
            
            if(cardIndex != null)
            {
                returnToDeck(cardIndex, playerChoiceAction(ActionHint.BOTTOM, ActionHint.TOP) == 1 ? DeckPosition.BOTTOM : DeckPosition.TOP);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
