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

public final class SPELL_G_FuriousCooking extends Card {

    public SPELL_G_FuriousCooking()
    {
        setImageSets("WXDi-P09-076", "SPDi01-106");

        setOriginalName("激食");
        setAltNames("ゲキショク Gekishoku");
        setDescription("jp",
                "@[ベット]@ ― #C #C\n\n" +
                "【エナチャージ２】をする。あなたがベットしていた場合、代わりに【エナチャージ３】をする。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Nourishment");
        setDescription("en",
                "Bet -- #C #C \n\n" +
                "[[Ener Charge 2]]. If you made a bet, instead [[Ener Charge 3]]." +
                "~#Vanish target SIGNI on your opponent's field with power 7000 or more."
        );
        
        setName("en_fan", "Furious Cooking");
        setDescription("en_fan",
                "@[Bet]@ - #C #C\n\n" +
                "[[Ener Charge 2]]. If you bet, [[Ener Charge 3]] instead." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );

		setName("zh_simplified", "激食");
        setDescription("zh_simplified", 
                "下注—#C #C（这张魔法使用时，可以作为使用费用追加把#C #C:支付）\n" +
                "[[能量填充2]]。你下注的场合，作为替代，[[能量填充3]]。" +
                "~#对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));

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

            spell = registerSpellAbility(this::onSpellEff);
            spell.setBetCost(new CoinCost(2));

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            enerCharge(!spell.hasUsedBet() ? 2 : 3);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}
