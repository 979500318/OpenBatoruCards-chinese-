package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_ArcgainHolyAngelPrincess extends Card {
    
    public SIGNI_W3_ArcgainHolyAngelPrincess()
    {
        setImageSets("WXDi-P01-033", "SPDi10-01", Mask.IGNORE+"SPDi08S-01");
        
        setOriginalName("聖天姫　アークゲイン");
        setAltNames("セイテンキアークゲイン Seitenki Aakugein");
        setDescription("jp",
                "@U：対戦相手のターンの間、このシグニは[[シャドウ]]を得る。\n" +
                "@E %W：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。" +
                "~#：@[@|どちらか１つを選ぶ。|@]@\n" +
                "$$1 対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。\n" +
                "$$2 カードを１枚引く。"
        );
        
        setName("en", "Arcgwyn, Blessed Angel Queen");
        setDescription("en",
                "@C: During your opponent's turn, this SIGNI gains [[Shadow]].\n" +
                "@E %W: Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order." +
                "~#Chose one --\n" +
                "$$1 Return target upped SIGNI on your opponent's field to its owner's hand.\n" +
                "$$2 Draw a card."
        );
        
        setName("en_fan", "Arcgain, Holy Angel Princess");
        setDescription("en_fan",
                "@C: During your opponent's turn, this SIGNI gains [[Shadow]].\n" +
                "@E %W: Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand. Put the rest on the bottom of your deck in any order." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and return it to their hand.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "圣天姬 大天使该隐");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，这只精灵得到[[暗影]]。（这只精灵不会被对战对手作为对象）\n" +
                "@E %W:从你的牌组上面看3张牌。从中把精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow());
        }
        
        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
                addToHand(target);
            } else {
                draw(1);
            }
        }
    }
}
