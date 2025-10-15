package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G1_MenkoFirstPlay extends Card {

    public SIGNI_G1_MenkoFirstPlay()
    {
        setImageSets("WXDi-P11-072", "SPDi38-15");

        setOriginalName("壱ノ遊　メンコ");
        setAltNames("イチノユウメンコ Ichi no Yuu Menko");
        setDescription("jp",
                "@U：このシグニがバトルによってシグニ１体をバニッシュしたとき、このシグニを場から手札に戻してもよい。そうした場合、【エナチャージ２】をする。"
        );

        setName("en", "Menko, First Play");
        setDescription("en",
                "@U: Whenever this SIGNI vanishes a SIGNI through battle, you may return this SIGNI on your field to its owner's hand. If you do, [[Ener Charge 2]]."
        );
        
        setName("en_fan", "Menko, First Play");
        setDescription("en_fan",
                "@U: Whenever this SIGNI banishes a SIGNI in battle, you may return this SIGNI from the field to your hand. If you do, [[Ener Charge 2]]."
        );

		setName("zh_simplified", "壹之游 拍洋画");
        setDescription("zh_simplified", 
                "@U :当这只精灵因为战斗把精灵1只破坏时，可以把这只精灵从场上返回手牌。这样做的场合，[[能量填充2]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getEvent().getSourceAbility() == null && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getCardIndex().isSIGNIOnField() && playerChoiceActivate() && addToHand(getCardIndex()))
            {
                enerCharge(2);
            }
        }
    }
}

