package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_R_MagnificentAbsoluteFlames extends Card {
    
    public SPELL_R_MagnificentAbsoluteFlames()
    {
        setImageSets("WXDi-D07-022");
        
        setOriginalName("荘厳の絶火");
        setAltNames("ソウゴンノゼッカ Sougon no zekka");
        setDescription("jp",
                "対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。あなたか対戦相手のデッキの上からカードを２枚トラッシュに置く。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2あなたか対戦相手のデッキの上からカードを４枚トラッシュに置く。"
        );
        
        setName("en", "Burning Calamity");
        setDescription("en",
                "Vanish target SIGNI on your opponent's field with power 12000 or less. Put the top two cards of your deck or your opponent's deck into their owner's trash." +
                "~#Choose one -- \n$$1 Vanish target upped SIGNI on your opponent's field. \n$$2 Put the top four cards of your deck or your opponent's deck into the trash."
        );
        
        setName("en_fan", "Magnificent Absolute Flames");
        setDescription("en_fan",
                "Target 1 of your opponent's SIGNI with power 12000 or less, and banish it. Put the top 2 cards of your or your opponent's deck into the trash." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Put the top 4 cards of your or your opponent's deck into the trash."
        );
        
		setName("zh_simplified", "庄严的绝火");
        setDescription("zh_simplified", 
                "对战对手的力量12000以下的精灵1只作为对象，将其破坏。从你或对战对手的牌组上面把2张牌放置到废弃区。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 从你或对战对手的牌组上面把4张牌放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1) + Cost.color(CardColor.BLACK, 1));
        
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)));
        }
        private void onSpellEff()
        {
            banish(spell.getTarget());

            millDeck(playerChoiceAction(ActionHint.OWN, ActionHint.OPPONENT) == 1 ? getOwner() : getOpponent(), 2);
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                millDeck(playerChoiceAction(ActionHint.OWN, ActionHint.OPPONENT) == 1 ? getOwner() : getOpponent(), 4);
            }
        }
    }
}
