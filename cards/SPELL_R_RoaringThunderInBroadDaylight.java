package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SPELL_R_RoaringThunderInBroadDaylight extends Card {
    
    public SPELL_R_RoaringThunderInBroadDaylight()
    {
        setImageSets("WXDi-P04-062");
        
        setOriginalName("白日の轟雷");
        setAltNames("ハクジツノゴウライ Hakujitsu no Gourai");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、それをバニッシュする。あなたのデッキの上からカードを３枚見る。その中からカード１枚を手札に加え、残りを好きな順番でデッキの一番下に置く。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。"
        );
        
        setName("en", "Roaring Sunshine");
        setDescription("en",
                "Vanish target SIGNI on your opponent's field. Look at the top three cards of your deck. Add a card from among them to your hand and put the rest on the bottom of your deck in any order." +
                "~#Choose one -- \n$$1 Vanish target SIGNI on your opponent's field with power 5000 or less. \n$$2 Target SIGNI on your opponent's field gains@>@C@#: This SIGNI cannot attack.@@@@until end of turn."
        );
        
        setName("en_fan", "Roaring Thunder in Broad Daylight");
        setDescription("en_fan",
                "Target 1 of your opponent's SIGNI, and banish it. Look at the top 3 cards of your deck, add 1 card from among them to your hand, and put the rest on the bottom of your deck in any order." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 5000 or less, and banish it.\n" +
                "$$2 Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't atack."
        );
        
		setName("zh_simplified", "白日的轰雷");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，将其破坏。从你的牌组上面看3张牌。从中把1张牌加入手牌，剩下的任意顺序放置到牌组最下面。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1) + Cost.color(CardColor.WHITE, 1) + Cost.colorless(1));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()));
        }
        private void onSpellEff()
        {
            banish(spell.getTarget());
            
            look(3);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
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
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
                banish(target);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
                if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            }
        }
    }
}
