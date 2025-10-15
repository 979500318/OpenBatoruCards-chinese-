package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.CardIndexSnapshot;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_R1_CodeArtRIceCooker extends Card {

    public SIGNI_R1_CodeArtRIceCooker()
    {
        setImageSets("WX24-P1-060");

        setOriginalName("コードアート　Sイハンキ");
        setAltNames("コードアートエスイハンキ Koodo Aato Esu Ihanki");
        setDescription("jp",
                "@E @[手札を１枚捨てる]@：対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。このコストでスペルを捨てた場合、代わりに対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Code Art R Ice Cooker");
        setDescription("en",
                "@E @[Discard 1 card from your hand]@: Target 1 of your opponent's SIGNI with power 3000 or less, and banish it. If you discarded a spell for this cost, instead, target 1 of your opponent's SIGNI with power 5000 or less, and banish it."
        );

		setName("zh_simplified", "必杀代号 炊饭器");
        setDescription("zh_simplified", 
                "@E 手牌1张舍弃:对战对手的力量3000以下的精灵1只作为对象，将其破坏。这个费用把魔法舍弃的场合，作为替代，对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(1), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            boolean hasDiscardedSpell = ((CardIndexSnapshot)getAbility().getCostPaidData().get()).getCardReference().getType() == CardType.SPELL;
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, !hasDiscardedSpell ? 3000 : 5000)).get();
            banish(target);
        }
    }
}
