package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K2_TsunawataMediumTrap extends Card {

    public SIGNI_K2_TsunawataMediumTrap()
    {
        setImageSets("WXK01-066");

        setOriginalName("中罠　ツナワタ");
        setAltNames("チュウビンツナワタ Chuubin Tsunawata");
        setDescription("jp",
                "@E：対戦相手のレベル３以下のシグニ１体を対象とし、ターン終了時まで、それの基本パワーを2000にする。"
        );

        setName("en", "Tsunawata, Medium Trap");
        setDescription("en",
                "@E: Target 1 of your opponent's level 3 or lower SIGNI, and until end of turn, its base power becomes 2000."
        );

		setName("zh_simplified", "中罠 走钢丝");
        setDescription("zh_simplified", 
                "@E :对战对手的等级3以下的精灵1只作为对象，直到回合结束时为止，其的基本力量变为2000。（那只精灵的力量+或-的场合，变为2000后再+或-）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(2);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY);
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
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI().withLevel(0,3)).get();
            setBasePower(target, 2000, ChronoDuration.turnEnd());
        }
    }
}
