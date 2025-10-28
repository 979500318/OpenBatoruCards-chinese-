package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.DamageBlockParams;
import open.batoru.data.ability.cost.EnerCost;

public final class ARTS_G_WorldInPeaceAndTranquility extends Card {

    public ARTS_G_WorldInPeaceAndTranquility()
    {
        setImageSets("WX25-P1-008", "WX25-P1-008U");

        setOriginalName("千里同風");
        setAltNames("ウィンブースター Uindo Buusutaa Wind Booster");
        setDescription("jp",
                "@[ブースト]@ -- %G %X %X\n\n" +
                "このターン、次にあなたがルリグによってダメージを受ける場合、代わりにダメージを受けない。あなたがブーストしていた場合、このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "World In Peace and Tranquility");
        setDescription("en",
                "@[Boost]@ -- %G %X %X\n\n" +
                "This turn, the next time you would be damaged by a LRIG, instead you aren't damaged. If you used Boost, this turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

        setName("zh_simplified", "千里同风");
        setDescription("zh_simplified", 
                "赋能—%G%X %X（这张必杀使用时，可以作为使用费用追加把%G%X %X支付）\n" +
                "这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。你赋能的场合，这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setAdditionalCost(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(2)));
        }

        private void onARTSEff()
        {
            blockNextDamage(DamageBlockParams.ofLRIG());
            
            if(arts.hasPaidAdditionalCost())
            {
                blockNextDamage(DamageBlockParams.ofSIGNI());
            }
        }
    }
}

