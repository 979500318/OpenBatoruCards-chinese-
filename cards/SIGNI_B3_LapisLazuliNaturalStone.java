package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B3_LapisLazuliNaturalStone extends Card {
    
    public SIGNI_B3_LapisLazuliNaturalStone()
    {
        setImageSets("WXDi-P03-071");
        
        setOriginalName("羅石　ラピスラズリ");
        setAltNames("ラセキラピスラズリ Raseki Rapisu Razuri");
        setDescription("jp",
                "@U：対戦相手のターンの間、このシグニがバニッシュされたとき、手札から赤の＜宝石＞のシグニ１枚を場に出してもよい。そのシグニの%P能力は発動しない。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、それをダウンする。対戦相手は手札を１枚捨てる。\n" +
                "$$2カードを２枚引く。"
        );
        
        setName("en", "Lapis Lazuli, Natural Crystal");
        setDescription("en",
                "@U: During your opponent's turn, when this SIGNI is vanished, you may put a red <<Jewel>> SIGNI from your hand onto your field. The @E abilities of SIGNI put onto your field this way do not activate." +
                "~#Choose one -- \n$$1 Down target SIGNI on your opponent's field. Your opponent discards a card. \n$$2 Draw two cards."
        );
        
        setName("en_fan", "Lapis Lazuli, Natural Stone");
        setDescription("en_fan",
                "@U: During your opponent's turn, when this SIGNI is banished, you may put 1 red <<Gem>> SIGNI from your hand onto the field. That SIGNI's @E abilities don't activate." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and down it. Your opponent discards 1 card from their hand.\n" +
                "$$2 Draw 2 cards."
        );
        
		setName("zh_simplified", "罗石 青金石");
        setDescription("zh_simplified", 
                "@U 对战对手的回合期间，当这只精灵被破坏时，可以从手牌把红色的<<宝石>>精灵1张出场。那只精灵的@E能力不能发动。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，将其#D。对战对手把手牌1张舍弃。\n" +
                "$$2 抽2张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.RED).withClass(CardSIGNIClass.GEM).fromHand().playable()).get();
            putOnField(cardIndex, Enter.DONT_ACTIVATE);
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
                down(target);
                
                discard(getOpponent(), 1);
            } else {
                draw(2);
            }
        }
    }
}
