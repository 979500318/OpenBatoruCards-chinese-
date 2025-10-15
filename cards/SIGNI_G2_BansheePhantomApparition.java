package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_BansheePhantomApparition extends Card {

    public SIGNI_G2_BansheePhantomApparition()
    {
        setImageSets("WX25-P1-095");

        setOriginalName("幻怪　バンシー");
        setAltNames("ゲンカイバンシー Genkai Banshii");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたがアーツを使用していた場合、【エナチャージ１】をする。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがルリグによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Banshee, Phantom Apparition");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you used ARTS this turn, [[Ener Charge 1]]." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a LRIG, instead you aren't damaged."
        );

		setName("zh_simplified", "幻怪 报丧女妖");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合你把必杀使用过的场合，[[能量填充1]]。" +
                "~#[[能量填充1]]。这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller())) > 0)
            {
                enerCharge(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            blockNextDamage(cardIndexSnapshot -> CardType.isLRIG(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
