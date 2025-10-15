package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.game.gfx.GFXZoneWall;

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
                "赋能—%G%X %X（这张必杀使用时，可以作为使用费用追加把%G%X %X:支付）\n" +
                "这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。你赋能的场合，这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
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
            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
            GFXZoneWall.attachToChronoRecord(record, new GFXZoneWall(getOwner(), CardLocation.LIFE_CLOTH, "LRIG", new int[]{100,205,100}));
            blockNextDamage(record, cardIndexSnapshot -> CardType.isLRIG(cardIndexSnapshot.getCardReference().getType()));
            
            if(arts.hasPaidAdditionalCost())
            {
                ChronoRecord record2 = new ChronoRecord(ChronoDuration.turnEnd());
                GFXZoneWall.attachToChronoRecord(record2, new GFXZoneWall(getOwner(), CardLocation.LIFE_CLOTH, "SIGNI", new int[]{50,205,100}));
                blockNextDamage(record2, cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
            }
        }
    }
}

