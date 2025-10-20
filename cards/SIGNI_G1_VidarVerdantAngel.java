package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G1_VidarVerdantAngel extends Card {

    public SIGNI_G1_VidarVerdantAngel()
    {
        setImageSets("WXDi-P16-078", "SPDi10-18");

        setOriginalName("翠天　ヴィーザル");
        setAltNames("スイテンヴィーザル Suiten Viizaru");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、アップ状態のこのシグニをダウンしてもよい。そうした場合、【エナチャージ１】をする。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Vidar, Jade Angel");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may down this upped SIGNI. If you do, [[Ener Charge 1]]." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Vidar, Verdant Angel");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, you may down this upped SIGNI. If you do, [[Ener Charge 1]]." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

		setName("zh_simplified", "翠天 维达尔");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以把竖直状态的这只精灵横置。这样做的场合，[[能量填充1]]。" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED) && playerChoiceActivate() && down(getCardIndex()))
            {
                enerCharge(1);
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
