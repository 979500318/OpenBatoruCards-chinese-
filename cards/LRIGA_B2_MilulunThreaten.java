package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B2_MilulunThreaten extends Card {

    public LRIGA_B2_MilulunThreaten()
    {
        setImageSets("WXDi-P14-025");

        setOriginalName("ミルルン☆スレトゥン");
        setAltNames("ミルルンスレトゥン Mirurun Suretoun");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをダウンする。このターンに対戦相手がスペルを使用していた場合、代わりにそれをバニッシュする。"
        );

        setName("en", "Milulun ☆ Threaten");
        setDescription("en",
                "@E: Down target SIGNI on your opponent's field. If your opponent has used a spell this turn, instead vanish it."
        );
        
        setName("en_fan", "Milulun☆Threaten");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and down it. If your opponent used a spell this turn, banish it instead."
        );

		setName("zh_simplified", "米璐璐恩☆吓你");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其横置。这个回合对战对手把魔法使用过的场合，作为替代，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MILULUN);
        setColor(CardColor.BLUE);
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

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            boolean hasUsedSpell = GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_SPELL && !isOwnCard(event.getCaller())) > 0;
            
            CardIndex target = playerTargetCard(new TargetFilter(hasUsedSpell ? TargetHint.BANISH : TargetHint.DOWN).OP().SIGNI()).get();
            if(hasUsedSpell) banish(target);
            else down(target);
        }
    }
}
