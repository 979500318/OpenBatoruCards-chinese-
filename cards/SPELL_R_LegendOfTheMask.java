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
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.CoinCost;

public final class SPELL_R_LegendOfTheMask extends Card {
    
    public SPELL_R_LegendOfTheMask()
    {
        setImageSets("WXDi-P07-068");
        
        setOriginalName("仮面の伝説");
        setAltNames("カメンのデンセツ Kamen no Densetsu");
        setDescription("jp",
                "@[ベット]@ ― #C #C\n\n" +
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。あなたがベットしていた場合、代わりに対戦相手のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：対戦相手のアップ状態のシグニを１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Masked Legend");
        setDescription("en",
                "Bet -- #C #C \n\n" +
                "Vanish target SIGNI on your opponent's field with power 8000 or less. If you made a bet, instead vanish target SIGNI on your opponent's field." +
                "~#Vanish target upped SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Legend of the Mask");
        setDescription("en_fan",
                "@[Bet]@ - #C #C\n\n" +
                "Target 1 of your opponent's SIGNI with power 8000 or less, and banish it. If you bet, target 1 of your opponent's SIGNI, and banish it." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );
        
		setName("zh_simplified", "假面的传说");
        setDescription("zh_simplified", 
                "下注—#C #C（这张魔法使用时，可以作为使用费用追加把#C #C:支付）\n" +
                "对战对手的力量8000以下的精灵1只作为对象，将其破坏。你下注的场合，作为替代，对战对手的精灵1只作为对象，将其破坏。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        
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
            spell.setBetCost(new CoinCost(2));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            TargetFilter filter = new TargetFilter(TargetHint.BANISH).OP().SIGNI();
            if(!spell.hasUsedBet()) filter = filter.withPower(0,8000);
            spell.setTargets(playerTargetCard(filter));
        }
        private void onSpellEff()
        {
            banish(spell.getTarget());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
