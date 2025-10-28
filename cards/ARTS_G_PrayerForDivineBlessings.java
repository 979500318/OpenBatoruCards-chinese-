package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.DamageBlockParams;
import open.batoru.data.ability.cost.TrashCost;

public final class ARTS_G_PrayerForDivineBlessings extends Card {

    public ARTS_G_PrayerForDivineBlessings()
    {
        setImageSets("WX24-P2-008", "WX24-P2-008U");

        setOriginalName("加持祈禱");
        setAltNames("エメラルドウィッシュ Emerarudo Uisshu Emerald Wish");
        setDescription("jp",
                "このアーツを使用する際、あなたのルリグデッキから緑のアーツ１枚をルリグトラッシュに置いてもよい。そうした場合、このアーツの使用コストは%G %G %G減る。\n\n" +
                "このターン、次とその次にあなたがダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Prayer for Divine Blessings");
        setDescription("en",
                "While using this ARTS, you may put 1 green ARTS from your LRIG deck into the LRIG trash. If you do, the use cost of this ARTS is reduced by %G %G %G.\n\n" +
                "This turn, the next time you would be damaged and the next time after that, instead you aren't damaged."
        );

        setName("zh_simplified", "加持祈祷");
        setDescription("zh_simplified", 
                "这张必杀使用时，可以从你的分身牌组把绿色的必杀1张放置到分身废弃区。这样做的场合，这张必杀的使用费用减%G %G %G。\n" +
                "这个回合，下一次和再下一次你受到伤害的场合，作为替代，不会受到伤害。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 3));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setReductionCost(new TrashCost(new TargetFilter().own().ARTS().withColor(CardColor.GREEN).except(cardId).fromLocation(CardLocation.DECK_LRIG)), Cost.color(CardColor.GREEN, 3));
        }
        
        private void onARTSEff()
        {
            blockNextDamage(new DamageBlockParams().withInstances(2).withGFX("generic", new int[]{50,205,50}));
        }
    }
}
