package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst;
import open.batoru.data.CardConst.*;

public final class LRIGA_B2_AyasTatamiFlip extends Card {

    public LRIGA_B2_AyasTatamiFlip()
    {
        setImageSets("WXDi-P09-034");

        setOriginalName("あーやの畳返し！");
        setAltNames("アーヤノタタミガエシ Aaya no Tatami Gaeshi");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それを裏向きにする。このターン終了時、この方法で裏向きにしたシグニを、同じ場所にシグニがない場合、表向きにする。同じ場所にシグニがある場合、トラッシュに置く。"
        );

        setName("en", "Aya's Tatami Flip!");
        setDescription("en",
                "@E: Turn target SIGNI on your opponent's field face down. At the end of this turn, if a SIGNI is not in the same position as the SIGNI turned face down this way, turn that SIGNI face up. However, if there is a SIGNI in the same position, put the SIGNI turned face down this way into the trash."
        );
        
        setName("en_fan", "Aya's Tatami Flip!");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and turn it face down. At the end of this turn, turn the SIGNI turned face down this way face up if there is no SIGNI in the same zone. If there is a SIGNI in the same zone, put it into the trash."
        );

		setName("zh_simplified", "亚弥的返场！");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其变为里向。这个回合结束时，这个方法里向的精灵，相同场所没有精灵的场合，变为表向。相同场所有精灵的场合，放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AYA);
        setColor(CardColor.BLUE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FLIP).OP().SIGNI()).get();
            
            if(flip(target, CardConst.CardFace.BACK))
            {
                callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                    if(CardLocation.isSIGNI(target.getLocation()) && !flip(target, CardConst.CardFace.FRONT))
                    {
                        trash(target);
                    }
                });
            }
        }
    }
}
