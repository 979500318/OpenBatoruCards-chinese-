package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_G1_AiyaiKineticTrump extends Card {

    public LRIGA_G1_AiyaiKineticTrump()
    {
        setImageSets("WXDi-P12-040");

        setOriginalName("アイヤイ　キネティックトランプ");
        setAltNames("アイヤイキネティックトランプ Aiyai Kinetikku Toranpu");
        setDescription("jp",
                "@E：【エナチャージ１】\n" +
                "@E：あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。"
        );

        setName("en", "Aiyai, Kinetic Card");
        setDescription("en",
                "@E: [[Ener Charge 1]].\n@E: Add up to one target SIGNI from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Aiyai Kinetic Trump");
        setDescription("en_fan",
                "@E: [[Ener Charge 1]]\n" +
                "@E: Target up to 1 SIGNI from your ener zone, and add it to your hand."
        );
        
		setName("zh_simplified", "艾娅伊 动能抽取");
        setDescription("zh_simplified", 
                "@E :[[能量填充1]]\n" +
                "@E :从你的能量区把精灵1张最多作为对象，将其加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AIYAI);
        setColor(CardColor.GREEN);
        setLevel(1);
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
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
    }
}
