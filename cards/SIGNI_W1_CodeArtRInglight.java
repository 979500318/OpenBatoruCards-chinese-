package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W1_CodeArtRInglight extends Card {
    
    public SIGNI_W1_CodeArtRInglight()
    {
        setImageSets("WXDi-P04-045");
        
        setOriginalName("コードアート　Rングライト");
        setAltNames("コードアートアールングライト Koodo Aato Aa Ringuraito Ringlight");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、あなたの手札からスペル２枚を公開してもよい。そうした場合、カードを１枚引く。\n" +
                "@U：あなたのメインフェイズ開始時、あなたのデッキの一番上を公開する。それがスペルの場合、カードを１枚引く。"
        );
        
        setName("en", "R - Inglight, Code: Art");
        setDescription("en",
                "@U: When this SIGNI is vanished, you may reveal two spells from your hand. If you do, draw a card.\n" +
                "@U: At the beginning of your main phase, reveal the top card of your deck. If that card is a spell, draw a card."
        );
        
        setName("en_fan", "Code Art R Inglight");
        setDescription("en_fan",
                "@U: When this SIGNI is banished, you may reveal 2 spells from your hand. If you do, draw 1 card.\n" +
                "@U: At the beginning of your main phase, reveal the top card of your deck. If it is a spell, draw 1 card."
        );
        
		setName("zh_simplified", "必杀代号 环形灯");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，可以从你的手牌把魔法2张公开。这样做的场合，抽1张牌。\n" +
                "@U :你的主要阶段开始时，你的牌组最上面公开。其是魔法的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(1000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff1);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto.setCondition(this::onAutoEff2Cond);
        }
        
        private void onAutoEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.REVEAL).own().spell().fromHand());
            
            if(data.size() == 2 && reveal(data) == 2)
            {
                draw(1);
                addToHand(data);
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex == null || cardIndex.getIndexedInstance().getTypeByRef() != CardType.SPELL ||
               draw(1).get() == null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
    }
}
