package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class LRIGA_G2_MamaCunning extends Card {

    public LRIGA_G2_MamaCunning()
    {
        setImageSets("WXDi-P14-031");

        setOriginalName("ママ♥カンニング");
        setAltNames("ママカンニング Mama Kaningu");
        setDescription("jp",
                "@E：数字１つを宣言する。このターン、次にこの方法で宣言した数字と同じレベルの対戦相手のシグニ１体がアタックしたとき、そのシグニをバニッシュする。"
        );

        setName("en", "Mama ♥ Cheat");
        setDescription("en",
                "@E: Declare a number. The next time a SIGNI on your opponent's field with the same level as the declared number attacks this turn, vanish it."
        );
        
        setName("en_fan", "Mama♥Cunning");
        setDescription("en_fan",
                "@E: Declare 1 number. This turn, the next time 1 of your opponent's SIGNI with the same level as the declared number attacks, banish it."
        );

		setName("zh_simplified", "妈妈♥狡黠");
        setDescription("zh_simplified", 
                "@E :数字1种宣言。这个回合，当下一次与这个方法宣言数字相同等级的对战对手的精灵1只攻击时，将那只精灵破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAMA);
        setColor(CardColor.GREEN);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private int number;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            number = playerChoiceNumber(0,1,2,3,4,5) - 1;
            
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && caller.getIndexedInstance().getLevel().getValue() == number &&
                    CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            banish(caller);
            
            getAbility().disable();
        }
    }
}
