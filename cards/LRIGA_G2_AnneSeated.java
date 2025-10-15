package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_G2_AnneSeated extends Card {

    public LRIGA_G2_AnneSeated()
    {
        setImageSets("WXDi-P11-036");

        setOriginalName("アン － 座シ");
        setAltNames("アンザシ An Zashi");
        setDescription("jp",
                "@E：【エナチャージ１】\n" +
                "@E：このターン、次にあなたがダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Ann - Repose");
        setDescription("en",
                "@E: [[Ener Charge 1]].\n" +
                "@E: The next time you would take damage this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Anne Seated");
        setDescription("en_fan",
                "@E: [[Ener Charge 1]]\n" +
                "@E: This turn, the next time you would be damaged, instead you aren't damaged."
        );

		setName("zh_simplified", "安 - 座落");
        setDescription("zh_simplified", 
                "@E :[[能量填充1]]\n" +
                "@E :这个回合，下一次你受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            enerCharge(1);
        }
        private void onEnterEff2()
        {
            blockNextDamage();
        }
    }
}
