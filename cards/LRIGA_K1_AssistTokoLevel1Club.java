package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K1_AssistTokoLevel1Club extends Card {

    public LRIGA_K1_AssistTokoLevel1Club()
    {
        setImageSets("WXDi-CP01-016");

        setOriginalName("【アシスト】とこ　レベル１【棍】");
        setAltNames("アシストトコレベルイチコン Ashisuto Toko Reberu Ichi Kon Assist Toko");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。\n" +
                "@E：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "[Assist] Toko, Level 1 [Cudgel]");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --2000 power until end of turn.\n@E: Add target SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "[Assist] Toko Level 1 [Club]");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power.\n" +
                "@E: Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand."
        );

		setName("zh_simplified", "【支援】床 等级1【棍】");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n" +
                "@E 从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TOKO);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.BLACK);
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

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            addToHand(target);
        }
    }
}
