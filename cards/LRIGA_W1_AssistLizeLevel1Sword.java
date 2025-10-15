package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_W1_AssistLizeLevel1Sword extends Card {

    public LRIGA_W1_AssistLizeLevel1Sword()
    {
        setImageSets("WXDi-CP01-012");

        setOriginalName("【アシスト】リゼ　レベル１【剣】");
        setAltNames("アシストリゼレベルイチツルギ Ashisuto Rize Reberu Ichi Tsurugi Assist Lize");
        setDescription("jp",
                "@E：対戦相手のパワー10000以下のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "[Assist] Lize, Level 1 [Sword]");
        setDescription("en",
                "@E: Return target SIGNI on your opponent's field with power 10000 or less to its owner's hand."
        );
        
        setName("en_fan", "[Assist] Lize Level 1 [Sword]");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 10000 or less, and return it to their hand."
        );

		setName("zh_simplified", "【支援】莉泽 等级1【剑】");
        setDescription("zh_simplified", 
                "@E :对战对手的力量10000以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LIZE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,10000)).get();
            addToHand(target);
        }
    }
}
