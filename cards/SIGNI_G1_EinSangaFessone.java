package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_G1_EinSangaFessone extends Card {

    public SIGNI_G1_EinSangaFessone()
    {
        setImageSets("WXDi-P14-062");

        setOriginalName("アイン＝サンガ//フェゾーネ");
        setAltNames("アインサンガフェゾーネ Ain Sanga Fezoone");
        setDescription("jp",
                "@C：あなたの場にレベル３の覚醒状態のシグニがあるかぎり、このシグニは@>@U：このシグニがパワー10000以上のシグニとバトルしたとき、そのシグニをトラッシュに置く。@@を得る。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Sanga//Fesonne, Type: Eins");
        setDescription("en",
                "@C: As long as there is a level three awakened SIGNI on your field, this SIGNI gains@>@U: Whenever this SIGNI battles a SIGNI with power 10000 or more, put that SIGNI into its owner's trash.@@" +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Ein-Sanga//Fessone");
        setDescription("en_fan",
                "@C: As long as there is a level 3 awakened SIGNI on your field, this SIGNI gains:" +
                "@>@U: Whenever this SIGNI battles with a SIGNI with power 10000 or more, it is put into the trash.@@" +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

		setName("zh_simplified", "EINS=山河//音乐节");
        setDescription("zh_simplified", 
                "@C :你的场上有等级3的觉醒状态的精灵时，这只精灵得到\n" +
                "@>@U :当这只精灵与力量10000以上的精灵战斗时，那只精灵放置到废弃区。@@\n" +
                "。（在战斗的破坏前先发动）" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
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

            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().withLevel(3).withState(CardStateFlag.AWAKENED).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK_BATTLE, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return getEvent().getSourceCardIndex().getIndexedInstance().getPower().getValue() >= 10000 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff()
        {
            trash(getEvent().getSourceCardIndex());
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
